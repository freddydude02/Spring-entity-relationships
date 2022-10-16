package com.nology.java.consolidation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkerService {
	
	@Autowired
	private TempRepository tRepo;
	
	@Autowired
	private JobRepository jRepo;
	
	public List<Job> getAllJobs() {
		return jRepo.findAll();
	}

	public List<Job> getAllJobsTF(Boolean bool) {
		List<Job> all = getAllJobs();
		List<Job> filtered = new ArrayList<>();
		for (Job job: all) {
			if( bool == true) {
				if (job.findTemp() != null) {
					filtered.add(job);
				}
			}
			if(bool == false) {
				if (job.findTemp() == null) {
					filtered.add(job);
				}
			}
		}
		return filtered;
	}
	
	public List<Temp> getAllTemps() {
		return tRepo.findAll();
	}
	
	public List<Temp> getAllAvailableTemps (Long jobId) {
		
		Job fetchedJob = this.getJob(jobId);
		
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

	public void createJob(JobDTO job) {
		Job j = new Job(job.getName(),job.getStartDate(),job.getEndDate(),job.getTemp());
		jRepo.save(j);
		
	}
	
	public void createTemp(TempDTO temp) {
		Temp t = new Temp(temp.getFirstName(),temp.getLastName());
		tRepo.save(t);
	}
	
	public Job getJob(Long id) {
		return jRepo.findById(id).get();
	}
	public Temp getTemp(Long id) {
		return tRepo.findById(id).get();
	}
	
	public Job updateJob (Long jobId, JobDTO jobData) {
		Job fetchedJob = this.getJob(jobId);
		
		if(fetchedJob == null) return null;
		
		Job existentJob = fetchedJob;
		
		if(jobData.getName() != null && !jobData.getName().equals("")) {
			existentJob.setName(jobData.getName());
		}
		if(jobData.getStartDate() != null && !jobData.getStartDate().equals("")) {
			existentJob.setStartDate(jobData.getStartDate());
		}
		if(jobData.getEndDate() != null && !jobData.getEndDate().equals("")) {
			existentJob.setEndDate(jobData.getEndDate());
		}
		return this.jRepo.save(existentJob);
	}
	
	
	public Job assignJob (Long jobId,Long tempId) {
		Job fetchedJob = this.getJob(jobId);
		Temp fetchedTemp = this.getTemp(tempId);
		
		if(fetchedJob == null) return null;
		if(fetchedTemp == null) return null;
		
		Job existentJob = fetchedJob;
		Temp existentTemp = fetchedTemp;
	
//		zero check
		if(existentTemp.getJobArr().size() == 0) {
			existentJob.setTemp(existentTemp);
			return this.jRepo.save(existentJob);
		}
		LocalDate aS,aF,bS,bF;
		aS = existentJob.getStartDate();
		aF = existentJob.getEndDate();
	
//		sort jobArr
		ArrayList<Job> jobArr = new ArrayList<>();
		for (Job job : existentTemp.getJobArr()) {
			jobArr.add(job);
		}
		Collections.sort(jobArr, new Comparator<Job>() {
			public int compare(Job a, Job b) {
				return a.getStartDate().compareTo(b.getStartDate());
			}
		});
		
//		start and end checks
			bS = jobArr.get(0).getStartDate();
			bF = jobArr.get(jobArr.size() - 1).getEndDate();
			
			if (aF.isBefore(bS)) {
				existentJob.setTemp(existentTemp);
				return this.jRepo.save(existentJob);
			}
			if (bF.isBefore(aS)) {
				existentJob.setTemp(existentTemp);
				return this.jRepo.save(existentJob);
			}
			
//			in between checks
		for (int i = 0; i < jobArr.size() - 1; i++) {
		
			bF = jobArr.get(i).getEndDate();
			bS = jobArr.get(i + 1).getStartDate();
			if (bF.isBefore(aS) && aF.isBefore(bS)) {
				existentJob.setTemp(existentTemp);
				return this.jRepo.save(existentJob);
			}
		}
		
		return this.jRepo.save(existentJob);
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
	
	public void deleteJob (Long id) {
		jRepo.deleteById(id);
	}
	
	public void deleteTemp (Long id) {
		tRepo.deleteById(id);
	}
	
	
	
}
