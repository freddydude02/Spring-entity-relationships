package com.nology.java.consolidation.temp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nology.java.consolidation.job.Job;
import com.nology.java.consolidation.job.JobService;

@Service
@Transactional
public class TempService {
	
	@Autowired
	private TempRepository tRepo;
	
	private JobService jobService;
	
	
	public List<Temp> getAllTemps() {
		return tRepo.findAll();
	}
	
	public List<Temp> getAllAvailableTemps (Long jobId) {
		
		Job fetchedJob = this.jobService.getJob(jobId);
		
		if(fetchedJob == null) return null;
		
		Job existentJob = fetchedJob;
		ArrayList<Temp> tempList = new ArrayList<>();
		
		System.out.println(getAllTemps().size());
		
		for (Temp temp: getAllTemps()) {
			
//			zero check
			if(temp.getJobArr().size() == 0) {
				System.out.println("this has zero");
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
	
	public void createTemp(TempDTO temp) {
		Temp t = new Temp(temp.getFirstName(),temp.getLastName());
		tRepo.save(t);
	}
	
	public Temp getTemp(Long id) {
		return tRepo.findById(id).get();
	}
	
	
	public Temp updateTemp (Long tempId, TempDTO tempData) {
		Temp fetchedTemp = this.getTemp(tempId);
		
		if(fetchedTemp == null) return null;
		
		Temp existentTemp = fetchedTemp;
		
		if(tempData.getFirstName() != null && !tempData.getFirstName().equals("")) {
			existentTemp.setFirstName(tempData.getFirstName());
		}
		if(tempData.getLastName() != null && !tempData.getLastName().equals("")) {
			existentTemp.setLastName(tempData.getLastName());
		}

		return this.tRepo.save(existentTemp);
	}
	
	public void deleteTemp (Long id) {
		tRepo.deleteById(id);
	}
	
	
	
}
