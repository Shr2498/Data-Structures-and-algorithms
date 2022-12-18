// Project 4 : List Scheduling and Sorted List Scheduling for LoadBalancing
// Name : Bandaru Sai Venkata Shreyas
// CSUN ID : 203140710

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Project4
 {	
	public static void LS(List<Integer> processList, List<String> answer) {
		
		int m = 0, m1 = 0;
		for (Integer task : processList) {
			if (m > m1) 
			{
				m1 = m1 + task;
			} 
			else if (m < m1) 
			{
				m = m + task;
			} 
			else if (m == m1) 
			{
				m = m + task;
			}
		}
		if (m != m1) {
			answer.add(m > m1 ? String.valueOf(m) : String.valueOf(m1));
		}

	}

	public static void main(String[] args) throws Exception 
	{
		File f = new File("input.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		int n = Integer.valueOf(br.readLine());
		List<Integer> ip = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ip.add(Integer.valueOf(br.readLine()));
		}
		List<String> answer = new ArrayList<>();
		LS(ip, answer);
		Collections.sort(ip, Collections.reverseOrder());
		LS(ip, answer);
		System.out.println(answer);
	}
}


