import java.util.Arrays;

public class project_2 {
	
	
	
    static int frames = 3;  // Total number of frames for each page fault algorithm 
    
    
    
	public static int OPT(String page_Reference_String) {  // Optimal page replacement algorithm method
		
	    String[] reference_Number = page_Reference_String.split(",");  // With split() method, we can split each reference number after a comma
	    
	    int pageFaults = 0;  // Keeps track of the number of page faults that occur during the execution of the algorithm
	    int currentRefrenceDigit = 0;
	    int[] pageTable = new int[frames];  // Keeps track of frames that are being used in the page table
	    Arrays.fill(pageTable, -1);  // using built in array fill method to fill the pageTable array with -1 meaning an empty frame
	    
	    int[] reference = new int[reference_Number.length];  // creating an integer [] to store the string reference numbers
	    for (int i = 0; i < reference_Number.length; i++) {
	        reference[i] = Integer.parseInt(reference_Number[i]);  // converting from string to integer and storing in into the array
	    }
	    	    
	    for (int i = 0; i < reference.length; i++) {  // loop through each reference integer
	    	
	        boolean found_Reference_Number = false;  // Keep track of whether a frame exists in the frameArray or not
	        
	        for (int j = 0; j < pageTable.length; j++) {  // loop through page table
	            if (pageTable[j] == reference[i]) {  // checks if current reference string exist in the table or not
	                found_Reference_Number = true;  // if exist, set found_Reference_Number to true and break out of the for loop
	                break;
	            }
	        }
	        
	        if (found_Reference_Number  == true) {  // found_Reference_Number == true continue, else break based on reference number being in the page table
	            continue;
	        }
	        
	        if (currentRefrenceDigit < frames) {  // checks if currentRefrenceDigit is less than then frames in page table
	            pageTable[currentRefrenceDigit++] = reference[i];  // if it is add currentRefrenceDigit to the page table
	        } 
	        
	        else {
	        	
	            int farthest_reference_string = -1; // Initializes a variable to keep track of the farthest reference string in the page table and sets it to -1
	            int replaced = -1; // Initializes a variable to keep track of the reference string being replaced and sets it to -1

	            for (int j = 0, k; j < frames; j++) { // Loop through each frame in the page table
	            	
	                for (k = i + 1; k < reference.length && pageTable[j] != reference[k]; k++); // Loop through the reference string starting from the current position and finds the index of the first occurrence of the page in the reference string
	                
	                	if (k == reference.length || k > farthest_reference_string) { // If the page is not found in the reference string or its index is greater than the farthest reference string index
	                		farthest_reference_string = k; // Update the farthest reference string index to the index of the found page
	                		replaced = j; // Update the index of the page to be replaced to the current frame index
	                	}
	                	
	                if (k == reference.length) 
	                	break; // Break out of the loop if the page is not found in the reference string
	            }

	            pageTable[replaced] = reference[i]; // Replace the reference string in the page table with the current reference string
	        }

	        pageFaults++;  // increase page fault by 1
	    }  
	    return pageFaults;  // return total number of page fault in the execution of the algorithm
	}


	
	public static int FIFO(String page_Reference_String) {  // First Come First Serve page replacement algorithm method
		
	    String[] reference_Number = page_Reference_String.split(",");  // With split() method, we can split each reference number after a comma

	    int pageFaults = 0;  // Keeps track of the number of page faults that occur during the execution of the algorithm
	    int oldestFrame = 0;  // Keeps track of the oldest frame in memory
	    
	    int[] pageTable = new int[frames];  // Keeps track of frames that are being used in the page table
	    Arrays.fill(pageTable, -1);  // using built in array fill method to fill the pageTable array with -1 meaning an empty frame

	    for (String reference_number : reference_Number) {  // Loop through each reference number until there are no more left
	    	
	        int new_reference_number = Integer.parseInt(reference_number.trim());  // Get the new reference number in the string
	        
	        boolean found_Reference_Number = false;  // Keep track of whether a frame exists in the frameArray or not
	        
	        for (int i = 0; i < frames; i++) {  // Loop through the frames
	            if (pageTable[i] == new_reference_number) {  // If the new reference number is found in the loop then it will stay in the frame
	                found_Reference_Number = true;
	                break;  // Break out of the loop
	            }
	        }
	        
	        if (found_Reference_Number == false) {  // If the new reference number is not found in the frameArray
	            pageFaults++;  // Page fault increases by 1
	            pageTable[oldestFrame] = new_reference_number;  // Oldest frame is replaced by the new reference number
	            oldestFrame = (oldestFrame + 1) % frames;  // Next oldest frame is selected in the frame array
	        }       
	    }    
	    return pageFaults;  // Return the total number of page faults found during the execution of the FIFO page fault algorithm
	}
	
	
	
	public static int LRU(String page_Reference_String) {  // Least Recently Used page replacement algorithm method, very similar to FIFO method

	    String[] reference_Number = page_Reference_String.split(",");  // With split() method, we can split each reference number after a comma

	    int pageFaults = 0; // Keeps track of the number of page faults that occur during the execution of the algorithm

	    int[] pageTable = new int[frames]; // Keeps track of frames that are being used in the page table
	    Arrays.fill(pageTable, -1); // using built in array fill method to fill the pageTable array with -1 meaning an empty frame

	    int[] lastUsed = new int[frames]; // Keeps track of the last time each frame was used
	    Arrays.fill(lastUsed, -1); // Initialize all values to -1

	    for (int i = 0; i < reference_Number.length; i++) {  // loop through each reference number
	        int new_reference_number = Integer.parseInt(reference_Number[i].trim()); // Get the new reference number in the string

	        boolean found_Reference_Number = false; // Keep track of whether a frame exists in the frameArray or not

	        // Check if the page is already in a frame
	        for (int j = 0; j < frames; j++) {
	            if (pageTable[j] == new_reference_number) {
	                found_Reference_Number = true;
	                lastUsed[j] = i; // Update the time this frame was last used
	                break;
	            }
	        }

	        if (found_Reference_Number  == false) {  // If the page is not already in a frame, find the least recently used frame and replace it

	            pageFaults++; // Page fault increases by 1

	            // Find the least recently used frame with this for loop 
	            int leastRecentlyUsedFrame = 0;
	            for (int j = 1; j < frames; j++) {  // iterate through the page table frame column
	                if (lastUsed[j] < lastUsed[leastRecentlyUsedFrame]) {
	                    leastRecentlyUsedFrame = j;
	                }
	            }

	            pageTable[leastRecentlyUsedFrame] = new_reference_number;  // Replace the least recently used frame with the new reference number
	            lastUsed[leastRecentlyUsedFrame] = i; // Update the time this frame was last used
	        }
	    }
	    return pageFaults; // Return the total number of page faults found during the execution of the LRU page fault algorithm
	}
	
	
	
	public static int SEC(String page_Reference_String) {  // Second Chance page replacement algorithm method, similar to LRU and FIFO
		
	    String[] reference_Number = page_Reference_String.split(",");  // With split() method, we can split each reference number after a comma
		
	    int pageFaults = 0;  // Keeps track of the number of page faults that occur during the execution of the algorithm
	    int currentReferenceDigit = 0;  // keep track of the current reference number in the page table
	    
	    int[] pageTable = new int[frames];  // Keeps track of frames that are being used in the page table
	    Arrays.fill(pageTable, -1);  // using built in array fill method to fill the pageTable array with -1 meaning an empty frame
	    
	    boolean[] second_chance = new boolean[frames];  // if a reference number repeats then second chance is given to that number

	    for (int i = 0; i < reference_Number.length; i++) {  // loop through each reference number
	    	
	        int new_reference_number = Integer.parseInt(reference_Number[i]);  // Get the new reference number in the string
	        boolean found_Reference_Number = false;  // Keep track of whether a frame exists in the frameArray or not
	        
	        for (int j = 0; j < frames; j++) {  // loop through all the reference frame
	            if (pageTable[j] == new_reference_number) {  // check if the new_reference_number exist in the table or not
	                second_chance[j] = true;  // give the new_reference_number a second chance
	                found_Reference_Number = true;  
	                break;  // break out of the loop
	            }
	        }

	        if (found_Reference_Number  == false) {  // if the new_reference_number is not found in the page Table
	        	
	            for (;;) {  // Infinite loop that continues until the reference number is successfully added to the page table
	                if (second_chance[currentReferenceDigit]  == false) {  // Check if the current page has not been referenced recently  
	                    pageTable[currentReferenceDigit] = new_reference_number;  // Add the new reference number to the current page
	                    currentReferenceDigit = (currentReferenceDigit + 1) % frames;  // update the current reference number
	                    pageFaults++;  // Increase the count of page fault by +1
	                    break;  // break out of the if loop
	                }
	                
	                else {  // resetting the second chance for the reference number digit
	                    second_chance[currentReferenceDigit] = false;
	                    currentReferenceDigit = (currentReferenceDigit + 1) % frames;
	                }        
	            }
	        }
	        
	    }
	    return pageFaults;  // Return the total number of page faults found during the execution of the FIFO page fault algorithm
	}

	

	public static void main(String[] args) {
				
		// reference string #1
		String page_reference_string_1 = "2,6,9,2,4,2,1,7,3,0,5,2,1,2,9,5,7,3,8,5";
		System.out.print("Page-Reference String: " + page_reference_string_1);
		System.out.print("\nOPT: " + OPT(page_reference_string_1) + "\t\tFIFO: " + FIFO(page_reference_string_1) + 
				"\tLRU: " + LRU(page_reference_string_1) + "\t\tSEC: " + SEC(page_reference_string_1));
		
		// reference string #2
		String page_reference_string_2 = "0,6,3,0,2,6,3,5,2,4,1,3,0,6,1,4,2,3,5,7";
		System.out.print("\n\nPage-Reference String: " + page_reference_string_2);
		System.out.print("\nOPT: " + OPT(page_reference_string_2) + "\t\tFIFO: " + FIFO(page_reference_string_2) + 
				"\tLRU: " + LRU(page_reference_string_2) + "\t\tSEC: " + SEC(page_reference_string_2));
		
		// reference string #3
		String page_reference_string_3 = "3,1,4,2,5,4,1,3,5,2,0,1,1,0,2,3,4,5,0,1";
		System.out.print("\n\nPage-Reference String: " + page_reference_string_3);
		System.out.print("\nOPT: " + OPT(page_reference_string_3) + "\t\tFIFO: " + FIFO(page_reference_string_3) + 
				"\tLRU: " + LRU(page_reference_string_3) + "\t\tSEC: " + SEC(page_reference_string_3));
		
		// reference string #4
		String page_reference_string_4 = "4,2,1,7,9,8,3,5,2,6,8,1,0,7,2,4,1,3,5,8";
		System.out.print("\n\nPage-Reference String: " + page_reference_string_4);
		System.out.print("\nOPT: " + OPT(page_reference_string_4) + "\t\tFIFO: " + FIFO(page_reference_string_4) + 
				"\tLRU: " + LRU(page_reference_string_4) + "\t\tSEC: " + SEC(page_reference_string_4));	
		
		// reference string #5
		String page_reference_string_5 = "0,1,2,3,4,4,3,2,1,0,0,1,2,3,4,4,3,2,1,0";
		System.out.print("\n\nPage-Reference String: " + page_reference_string_5);
		System.out.print("\nOPT: " + OPT(page_reference_string_5) + "\t\tFIFO: " + FIFO(page_reference_string_5) + 
				"\tLRU: " + LRU(page_reference_string_5) + "\t\tSEC: " + SEC(page_reference_string_5));
		
	}
}
