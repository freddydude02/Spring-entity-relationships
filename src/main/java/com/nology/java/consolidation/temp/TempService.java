package com.nology.java.consolidation.temp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nology.java.consolidation.job.Job;
import com.nology.java.consolidation.job.JobRepository;
import com.nology.java.consolidation.job.JobService;

@Service
@Transactional
public class TempService {
	
	@Autowired
	private TempRepository tRepo;
	
	@Autowired
	private JobRepository jRepo;
	
	
	public List<Temp> getAllTemps() {
		return tRepo.findAll();
	}
	
	public List<Temp> getAllAvailableTemps (Long jobId) {
		
		
		
		Optional<Job> fetchedJob = jRepo.findById(jobId);
		
		if(fetchedJob.isEmpty()) return null;
		
		Job existentJob = jRepo.findById(jobId).get();
		
		ArrayList<Temp> tempList = new ArrayList<>();
		
		for (Temp temp: getAllTemps()) {
			
//			zero check
			if(temp.getJobArr().size() == 0) {
				tempList.add(temp);
				continue;
			}
			LocalDate aS,aF,bS,bF;
			aS = existentJob.getStartDate();
			aF = existentJob.getEndDate();
			
//			sorted jobArr
			ArrayList<Job> jobArr = new ArrayList<>();
			for (Job job : temp.getJobArr()) {
				jobArr.add(job);
				continue;
			}
			Collections.sort(jobArr, new Comparator<Job>() {
				public int compare(Job a, Job b) {
					return a.getStartDate().compareTo(b.getStartDate());
				}
			});
			
//			start and end checks
			bS = jobArr.get(0).getStartDate();
			bF = jobArr.get(jobArr.size() - 1).getEndDate();
			if (aF.isBefore(bS)) {
				tempList.add(temp);
				continue;
			}
			if (bF.isBefore(aS)) {
				tempList.add(temp);
				continue;
			}
			
//			in between checks
			for (int i = 0; i < jobArr.size() - 1; i++) {
				bF = jobArr.get(i).getEndDate();
				bS = jobArr.get(i + 1).getStartDate();
				if (bF.isBefore(aS) && aF.isBefore(bS)) {
				tempList.add(temp);
				continue;
				}
			}
		}
		return tempList;
	}
	
	public Temp createTemp(TempDTO temp) {
		Temp t = new Temp(temp.getFirstName(),temp.getLastName());
		return tRepo.save(t);
	}
	
	public Optional<Temp> getTemp(Long id) {
		return tRepo.findById(id);
	}

	public Temp updateTemp (Long tempId, TempDTO tempData) {
		Optional<Temp> fetchedTemp = getTemp(tempId);
		
		if(fetchedTemp.isEmpty()) return null;
		
		Temp existentTemp = tRepo.findById(tempId).get();
		
		if(tempData.getFirstName() != null && !tempData.getFirstName().equals("")) {
			existentTemp.setFirstName(tempData.getFirstName());
		}
		if(tempData.getLastName() != null && !tempData.getLastName().equals("")) {
			existentTemp.setLastName(tempData.getLastName());
		}
		return this.tRepo.save(existentTemp);
	}
	
	public String deleteTemp (Long id) {
		Optional<Temp> fetchedTemp = getTemp(id);
		if(fetchedTemp.isEmpty()) return null;
		tRepo.deleteById(id);
		return "";
	}
	
	
	
}
