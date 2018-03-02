/* NiceSimulator.java
   CSC 225 - Summer 2017

   An empty shell of the operations needed by the NiceSimulator
   data structure.

   B. Bird - 06/11/2017
*/


import java.io.*;
import java.util.Arrays;

public class NiceSimulator{
	int max;
	int currMax = 0;
	int currMax2 = 0;
	nodeobj[] heapObj;
	int[] heapIndex;
	int[] heapPriority;
	int[] priorityIndex;

	public static final int SIMULATE_IDLE = -2;
	public static final int SIMULATE_NONE_FINISHED = -1;


	/* Constructor(maxTasks)
	   Instantiate the data structure with the provided maximum
	   number of tasks. No more than maxTasks different tasks will
	   be simultaneously added to the simulator, and additionally
	   you may assume that all task IDs will be in the range
	     0, 1, ..., maxTasks - 1
	*/
	public NiceSimulator(int maxTasks){
//initialize data structures and fill them with negative numbers
		max = maxTasks;
		heapIndex = new int[maxTasks+1];
		Arrays.fill(heapIndex, -3);
		priorityIndex = new int[maxTasks*2];
		heapPriority = new int[maxTasks+1];
		heapObj = new nodeobj[maxTasks*2];
		Arrays.fill(heapPriority, -7);
		Arrays.fill(priorityIndex, -1);


	}

	/* taskValid(taskID)
	   Given a task ID, return true if the ID is currently
	   in use by a valid task (i.e. a task with at least 1
	   unit of time remaining) and false otherwise.

	   Note that you should include logic to check whether
	   the ID is outside the valid range 0, 1, ..., maxTasks - 1
	   of task indices.

	*/

//O(1)
	public boolean taskValid(int taskID){
		//checks if value in heapIndex is greater or equal to zero
			if (heapIndex[taskID] >= 0){
					return true;
			}

			return false;
	}

	/* getPriority(taskID)
	   Return the current priority value for the provided
	   task ID. You may assume that the task ID provided
	   is valid.

	*/

//O(1)
	public int getPriority(int taskID){
		//returns the priority of the object with the declared taskID
		return heapObj[heapIndex[taskID]].priority;
	}

	/* getRemaining(taskID)
	   Given a task ID, return the number of timesteps
	   remaining before the task completes. You may assume
	   that the task ID provided is valid.

	*/

//O(1)
	public int getRemaining(int taskID){
		//returns the timeRequirement remaining from the object with the declared taskID
		return (heapObj[heapIndex[taskID]].timeRequirement);
	}


	/* add(taskID, time_required)
	   Add a task with the provided task ID and time requirement
	   to the system. You may assume that the provided task ID is in
	   the correct range and is not a currently-active task.
	   The new task will be assigned nice level 0.
	*/

//O(log(n))
	public void add(int taskID, int time_required){
//The add operation has the form: <time> add <task ID> <time requirement>

// System.out.println("add start");
// System.out.println(heapPriority[1]);
// System.out.println(heapPriority[2]);
// System.out.println(heapPriority[3]);
// System.out.println(heapPriority[4]);
// System.out.println(heapPriority[5]);
//Adds to heapObj
//adds the new object into the object array
		heapObj[currMax2] = new nodeobj(taskID, 0, time_required, 0);
//adds the index of the new heap object at the index of the taskID
		heapIndex[taskID] = currMax2;
//Adds the index of the new heap object to the bottom of the heap
	heapPriority[currMax + 1] = currMax2;
//Adds location in the heap of the new heap object in priorityIndex at the same index as its heap object
	priorityIndex[currMax2] = currMax + 1;

//starts to bubble up the new object in the heap
	int index = currMax + 1;
	int temp;
	int loop = 0;
	if(index > 1){
		while(loop == 0){
			if((heapObj[heapPriority[index]].priority) <= (heapObj[heapPriority[index/2]].priority)){
				if((heapObj[heapPriority[index]].priority) == (heapObj[heapPriority[index/2]].priority)){
					if((heapObj[heapPriority[index]].taskID) < (heapObj[heapPriority[index/2]].taskID)){
						temp = priorityIndex[heapPriority[index]];
						priorityIndex[heapPriority[index]] = index / 2;
						priorityIndex[heapPriority[index/2]] = temp;

						temp = heapPriority[index/2];
						heapPriority[index/2] = heapPriority[index];
						heapPriority[index] = temp;

					//increment
						index = index/2;
						if(index <= 1){
							break;
						}
					}
				else{
					break;
				}
				}

				else if((heapObj[heapPriority[index]].priority) < (heapObj[heapPriority[index/2]].priority)){
					temp = priorityIndex[heapPriority[index]];
					priorityIndex[heapPriority[index]] = index / 2;
					priorityIndex[heapPriority[index/2]] = temp;

					temp = heapPriority[index/2];
					heapPriority[index/2] = heapPriority[index];
					heapPriority[index] = temp;

					//increment
					index = index/2;
					if(index <= 1){
						break;
					}
				}

				else{
					break;
				}
			}
			else{
				break;
			}
		}
}
//increment the currMax(current max position of heap) and the currMax2(current max of the object array)
	currMax++;
	currMax2++;
	// System.out.println("currMax "+ currMax);
	// System.out.println("add end");
	// System.out.println(heapPriority[1]);
	// System.out.println(heapPriority[2]);
	// System.out.println(heapPriority[3]);
	// System.out.println(heapPriority[4]);
	// System.out.println(heapPriority[5]);
	}


	/* kill(taskID)
	   Delete the task with the provided task ID from the system.
	   You may assume that the provided task ID is in the correct
	   range and is a currently-active task.
	*/

//O(log(n))
	public void kill(int taskID){
//The kill operation has the form: <time> kill <task ID>

//if the task being killed is the last task in the heap
		if((priorityIndex[heapIndex[taskID]]) == currMax){
// System.out.println("kill if1");
// System.out.println(heapPriority[1]);
// System.out.println(heapPriority[2]);
// System.out.println(heapPriority[3]);
// System.out.println(heapPriority[4]);
// System.out.println("kill if2 ");
			priorityIndex[heapIndex[taskID]] = -1;
			heapIndex[heapObj[heapPriority[currMax]].taskID] = -1;
			heapIndex[taskID] = -6;
			heapPriority[currMax] = -7;
// System.out.println(heapPriority[1]);
// System.out.println(heapPriority[2]);
// System.out.println(heapPriority[3]);
// System.out.println(heapPriority[4]);
// System.out.println("kill if3");
		currMax--;
		}

//if the task being killed is not the last task in the heap, switch the task to
//be killed with the last element and delete the last element of the heap.
//then bubble down the element in the old location of the deleted task
		else{
// System.out.println("kill esle1");
// System.out.println(heapPriority[1]);
// System.out.println(heapPriority[2]);
// System.out.println(heapPriority[3]);
// System.out.println(heapPriority[4]);
// System.out.println("kill esle2");
		heapPriority[priorityIndex[heapIndex[taskID]]] = heapPriority[currMax];
		priorityIndex[heapPriority[currMax]] = priorityIndex[heapIndex[taskID]];
		int d = priorityIndex[heapPriority[currMax]];
		priorityIndex[heapIndex[taskID]] = -1;
		heapIndex[taskID] = -9;
		heapPriority[currMax] = -7;
		currMax--;

	//bubble
// System.out.println(heapPriority[1]);
// System.out.println(heapPriority[2]);
// System.out.println(heapPriority[3]);
// System.out.println(heapPriority[4]);
// System.out.println("kill esle3");
// System.out.println("bubble down start kill");
		int temp;
		int loop = 0;
		int left = 0;
		int right = 0;
		int equalleft = 0;
		int equalright = 0;
		while(loop == 0){
// System.out.println(heapPriority[1]+" "+priorityIndex[heapPriority[1]]);
// System.out.println(heapPriority[2]+" "+priorityIndex[heapPriority[2]]);
// System.out.println(heapPriority[3]+" "+priorityIndex[heapPriority[3]]);
// //System.out.println(heapPriority[4]+" "+priorityIndex[heapPriority[4]]);
// System.out.println();
//System.out.println(currMax);
//System.out.println("heap "+heapPriority[d*2]);
			if(((d*2 <= currMax) && (d*2 >= 0)) && (heapObj[heapPriority[d]].priority  >= heapObj[heapPriority[d*2]].priority)){
				left = 1;
			}
			if(((d*2+1 <= currMax) && (d*2+1 >= 0))  && (heapObj[heapPriority[d]].priority  >= heapObj[heapPriority[d*2+1]].priority)){
				right = 1;
			}
//Both the left and right children are valid swaps
			if(left == 1 && right == 1){
				if(heapObj[heapPriority[d*2]].priority  < heapObj[heapPriority[d*2+1]].priority){
					//sawp with left
					temp = heapPriority[d*2];
					heapPriority[d*2] = heapPriority[d];
					heapPriority[d] = temp;

					temp = d*2;
					priorityIndex[heapPriority[d]] = d;
					priorityIndex[heapPriority[d*2]] = temp;

					d = temp;
				}

				else if(heapObj[heapPriority[d*2]].priority > heapObj[heapPriority[(d*2+1)]].priority){
					//swap with right
					temp = heapPriority[(d*2+1)];
					heapPriority[(d*2+1)] = heapPriority[d];
					heapPriority[d] = temp;

					temp = (d*2+1);
					priorityIndex[heapPriority[d]] = d;
					priorityIndex[heapPriority[(d*2+1)]] = temp;

					d = temp;
				}
//if the priority of both children are equal check their taskID
				else if(heapObj[heapPriority[d*2]].priority == heapObj[heapPriority[d*2+1]].priority){
					if((heapObj[heapPriority[d*2]].taskID  < heapObj[heapPriority[d*2+1]].taskID) && (heapObj[heapPriority[d*2]].priority  < heapObj[heapPriority[d]].priority)){
						//sawp with left
						temp = heapPriority[d*2];
						heapPriority[d*2] = heapPriority[d];
						heapPriority[d] = temp;

						temp = d*2;
						priorityIndex[heapPriority[d]] = d;
						priorityIndex[heapPriority[d*2]] = temp;

						d = temp;
					}

					else if((heapObj[heapPriority[d*2]].taskID > heapObj[heapPriority[(d*2+1)]].taskID) && (heapObj[heapPriority[(d*2+1)]].priority  < heapObj[heapPriority[d]].priority)){
						//swap with right
						temp = heapPriority[(d*2+1)];
						heapPriority[(d*2+1)] = heapPriority[d];
						heapPriority[d] = temp;

						temp = (d*2+1);
						priorityIndex[heapPriority[d]] = d;
						priorityIndex[heapPriority[(d*2+1)]] = temp;

						d = temp;
					}
					else if((heapObj[heapPriority[d*2]].taskID  < heapObj[heapPriority[d*2+1]].taskID) && (heapObj[heapPriority[d*2]].priority == heapObj[heapPriority[d]].priority) && (heapObj[heapPriority[d*2]].taskID < heapObj[heapPriority[d]].taskID)){
						//sawp with left
						temp = heapPriority[d*2];
						heapPriority[d*2] = heapPriority[d];
						heapPriority[d] = temp;

						temp = d*2;
						priorityIndex[heapPriority[d]] = d;
						priorityIndex[heapPriority[d*2]] = temp;

						d = temp;
					}

					else if((heapObj[heapPriority[d*2]].taskID > heapObj[heapPriority[(d*2+1)]].taskID) && (heapObj[heapPriority[(d*2+1)]].priority == heapObj[heapPriority[d]].priority) && (heapObj[heapPriority[d*2]].taskID < heapObj[heapPriority[d]].taskID)){
						//swap with right
						temp = heapPriority[(d*2+1)];
						heapPriority[(d*2+1)] = heapPriority[d];
						heapPriority[d] = temp;

						temp = (d*2+1);
						priorityIndex[heapPriority[d]] = d;
						priorityIndex[heapPriority[(d*2+1)]] = temp;

						d = temp;
					}
					else{
						break;
					}
				}
			}

//if the left child is the only valid swap
			else if(left == 1 && right == 0){
				//swap with left
				if(heapObj[heapPriority[d]].priority  > heapObj[heapPriority[d*2]].priority){
				temp = heapPriority[d*2];
				heapPriority[d*2] = heapPriority[d];
				heapPriority[d] = temp;

				temp = d*2;
				priorityIndex[heapPriority[d]] = d;
				priorityIndex[heapPriority[d*2]] = temp;

				d = temp;
			}
			else if(heapObj[heapPriority[d]].priority  == heapObj[heapPriority[d*2]].priority){
				if(heapObj[heapPriority[d]].taskID  > heapObj[heapPriority[d*2]].taskID){
			temp = heapPriority[d*2];
			heapPriority[d*2] = heapPriority[d];
			heapPriority[d] = temp;

			temp = d*2;
			priorityIndex[heapPriority[d]] = d;
			priorityIndex[heapPriority[d*2]] = temp;

			d = temp;
		}
		else{
			break;
		}
		}
			}

//if the only valid swap is the right child
			else if(left == 0 && right == 1){
				if(heapObj[heapPriority[d]].priority  > heapObj[heapPriority[d*2+1]].priority){
				//swap with right
				temp = heapPriority[(d*2+1)];
				heapPriority[(d*2+1)] = heapPriority[d];
				heapPriority[d] = temp;

				temp = (d*2+1);
				priorityIndex[heapPriority[d]] = d;
				priorityIndex[heapPriority[(d*2+1)]] = temp;

				d = temp;
			}
			else if(heapObj[heapPriority[d]].priority  == heapObj[heapPriority[d*2+1]].priority){
				if(heapObj[heapPriority[d]].taskID  > heapObj[heapPriority[d*2+1]].taskID){
			temp = heapPriority[(d*2+1)];
			heapPriority[(d*2+1)] = heapPriority[d];
			heapPriority[d] = temp;


			temp = (d*2+1);
			priorityIndex[heapPriority[d]] = d;
			priorityIndex[heapPriority[(d*2+1)]] = temp;

			d = temp;
		}
		else{
			break;
		}
		}
			}
			else{
				break;
			}

//reset the right and left child valid variables
			right = 0;
			left = 0;
		}
		}
	}


	/* renice(taskID, new_priority)
	   Change the priority of the the provided task ID to the new priority
       value provided. The change must take effect at the next simulate() step.
	   You may assume that the provided task ID is in the correct
	   range and is a currently-active task.

	*/
	public void renice(int taskID, int new_priority){
//The renice operation has the form: <time> renice <task ID> <new priority>

//Tells if the priority is increased or decreased
		int bool = 1;
		int inc;
		if(heapObj[heapIndex[taskID]].priority < new_priority){
			bool = 0;
		}

//changes the priority of the taskID given to new priority
		heapObj[heapIndex[taskID]].priority = new_priority;

//if priority is increased bubble down
		if(bool == 0){
			int d =priorityIndex[heapIndex[taskID]];
 		 	int temp;
			int loop = 0;
			int left = 0;
			int right = 0;
			int equalleft = 0;
			int equalright = 0;
			while(loop == 0){
// System.out.println(heapPriority[1]);
// System.out.println(heapPriority[2]);
// System.out.println(heapPriority[3]);
// System.out.println(heapPriority[4]);
// System.out.println();
// System.out.println(currMax);
				if(((d*2 <= currMax) && (d*2 >= 0)) && (heapObj[heapPriority[d]].priority  >= heapObj[heapPriority[d*2]].priority)){
					left = 1;
				}
				if(((d*2+1 <= currMax) && (d*2+1 >= 0))  && (heapObj[heapPriority[d]].priority  >= heapObj[heapPriority[d*2+1]].priority)){
					right = 1;
				}

//if both children are valid swaps
				if(left == 1 && right == 1){
					if(heapObj[heapPriority[d*2]].priority  < heapObj[heapPriority[d*2+1]].priority){
						//sawp with left
						temp = heapPriority[d*2];
						heapPriority[d*2] = heapPriority[d];
						heapPriority[d] = temp;

						temp = d*2;
						priorityIndex[heapPriority[d]] = d;
						priorityIndex[heapPriority[d*2]] = temp;

						d = temp;
					}

					else if(heapObj[heapPriority[d*2]].priority > heapObj[heapPriority[d*2+1]].priority){
						//swap with right
						temp = heapPriority[d*2+1];
						heapPriority[d*2+1] = heapPriority[d];
						heapPriority[d] = temp;

						temp = d*2+1;
						priorityIndex[heapPriority[d]] = d;
						priorityIndex[heapPriority[d*2+1]] = temp;

						d = temp;
					}

//if they have the same priority check their taskID
					else if(heapObj[heapPriority[d*2]].priority == heapObj[heapPriority[d*2+1]].priority){
						if((heapObj[heapPriority[d*2]].taskID  < heapObj[heapPriority[d*2+1]].taskID) && (heapObj[heapPriority[d*2]].priority < heapObj[heapPriority[d]].priority)){
							//sawp with left
							temp = heapPriority[d*2];
							heapPriority[d*2] = heapPriority[d];
							heapPriority[d] = temp;

							temp = d*2;
							priorityIndex[heapPriority[d]] = d;
							priorityIndex[heapPriority[d*2]] = temp;

							d = temp;
						}

						else if((heapObj[heapPriority[d*2]].taskID > heapObj[heapPriority[d*2+1]].taskID) && (heapObj[heapPriority[d*2+1]].priority < heapObj[heapPriority[d]].priority)){
							//swap with right
							temp = heapPriority[d*2+1];
							heapPriority[d*2+1] = heapPriority[d];
							heapPriority[d] = temp;

							temp = d*2+1;
							priorityIndex[heapPriority[d]] = d;
							priorityIndex[heapPriority[d*2+1]] = temp;

							d = temp;
						}
						else if((heapObj[heapPriority[d*2]].taskID  < heapObj[heapPriority[d*2+1]].taskID) && (heapObj[heapPriority[d*2]].priority == heapObj[heapPriority[d]].priority) && (heapObj[heapPriority[d*2]].taskID < heapObj[heapPriority[d]].taskID)){
							//sawp with left
							temp = heapPriority[d*2];
							heapPriority[d*2] = heapPriority[d];
							heapPriority[d] = temp;

							temp = d*2;
							priorityIndex[heapPriority[d]] = d;
							priorityIndex[heapPriority[d*2]] = temp;

							d = temp;
						}

						else if((heapObj[heapPriority[d*2]].taskID > heapObj[heapPriority[(d*2+1)]].taskID) && (heapObj[heapPriority[(d*2+1)]].priority == heapObj[heapPriority[d]].priority) && (heapObj[heapPriority[d*2]].taskID < heapObj[heapPriority[d]].taskID)){
							//swap with right
							temp = heapPriority[(d*2+1)];
							heapPriority[(d*2+1)] = heapPriority[d];
							heapPriority[d] = temp;

							temp = (d*2+1);
							priorityIndex[heapPriority[d]] = d;
							priorityIndex[heapPriority[(d*2+1)]] = temp;

							d = temp;
						}
						else{
							break;
						}
					}
				}

//if the left child is the only valid swap
				else if(left == 1 && right == 0){
					//swap with left
					if(heapObj[heapPriority[d]].priority > heapObj[heapPriority[d*2]].priority){
						temp = heapPriority[d*2];
						heapPriority[d*2] = heapPriority[d];
						heapPriority[d] = temp;

						temp = d*2;
						priorityIndex[heapPriority[d]] = d;
						priorityIndex[heapPriority[d*2]] = temp;
						d = temp;
				}
				else if(heapObj[heapPriority[d]].priority == heapObj[heapPriority[d*2]].priority){
					if(heapObj[heapPriority[d]].taskID > heapObj[heapPriority[d*2]].taskID){
						temp = heapPriority[d*2];
						heapPriority[d*2] = heapPriority[d];
						heapPriority[d] = temp;

						temp = d*2;
						priorityIndex[heapPriority[d]] = d;
						priorityIndex[heapPriority[d*2]] = temp;
						d = temp;
			}
			else{
				break;
			}
			}
				}

//if the right child is the only valid swap
				else if(left == 0 && right == 1){
					if(heapObj[heapPriority[d]].priority  > heapObj[heapPriority[d*2+1]].priority){
					//swap with right
					temp = heapPriority[d*2+1];
					heapPriority[d*2+1] = heapPriority[d];
					heapPriority[d] = temp;

					temp = d*2+1;
					priorityIndex[heapPriority[d]] = d;
					priorityIndex[heapPriority[d*2+1]] = temp;

					d = temp;
				}
				else if(heapObj[heapPriority[d]].priority  == heapObj[heapPriority[d*2+1]].priority){
					if(heapObj[heapPriority[d]].taskID  > heapObj[heapPriority[d*2+1]].taskID){
				temp = heapPriority[d*2+1];
				heapPriority[d*2+1] = heapPriority[d];
				heapPriority[d] = temp;

				temp = d*2+1;
				priorityIndex[heapPriority[d]] = d;
				priorityIndex[heapPriority[d*2+1]] = temp;

				d = temp;
			}
			else{
				break;
			}
			}
				}
				else{
					break;
				}
				right = 0;
				left = 0;
			}
// System.out.println("bubble down end");
// System.out.println(heapPriority[1]);
// System.out.println(heapPriority[2]);
// System.out.println(heapPriority[3]);
// System.out.println(heapPriority[4]);
// System.out.println();
		}

//if priority is decreased bubble up
		else if(bool == 1){
// System.out.println("bubble up start");
// System.out.println(heapPriority[1]);
// System.out.println(heapPriority[2]);
// System.out.println(heapPriority[3]);
// System.out.println(heapPriority[4]);
			int index = taskID;
			int r = priorityIndex[heapIndex[taskID]];
			int temp;
			if(r > 1){
				while(((heapObj[heapPriority[r]].priority) <= (heapObj[heapPriority[r/2]].priority)) && (r) > 0){
					if(((heapObj[heapPriority[r]].priority) == (heapObj[heapPriority[r/2]].priority)) && ((heapObj[heapPriority[r]].taskID) < (heapObj[heapPriority[r/2]].taskID))){
			//bubbles the heap by priority
						temp = heapPriority[r/2];
						int y = heapObj[heapPriority[r/2]].taskID;
						int x = r;
						heapPriority[r/2] = heapPriority[r];
						heapPriority[r] = temp;
						priorityIndex[heapIndex[index]] = priorityIndex[heapIndex[y]];
						priorityIndex[heapIndex[y]] = x;


						if(r <= 1){
							break;
						}
					}
					else if((heapObj[heapPriority[r]].priority) < (heapObj[heapPriority[r/2]].priority)){
						temp = heapPriority[r/2];
				 //
				 		int y = heapObj[heapPriority[r/2]].taskID;
				 		int x = r;
				 		heapPriority[r/2] = heapPriority[r];
				 		heapPriority[r] = temp;
				 		priorityIndex[heapIndex[index]] = priorityIndex[heapIndex[y]];
				 		priorityIndex[heapIndex[y]] = x;

				 		if(r <= 1){
					 		break;
				 		}
					}
					else{
						break;
					}
				 	r = r/2;
				 if(r <= 1){
					 break;
				 }
				}
// System.out.println("bubble up end");
// System.out.println(heapPriority[1]);
// System.out.println(heapPriority[2]);
// System.out.println(heapPriority[3]);
// System.out.println(heapPriority[4]);
			}
		}
	}

	/* simulate()
	   Run one step of the simulation:
		 - If no tasks are left in the system, the CPU is idle, so return
		   the value SIMULATE_IDLE.
		 - Identify the next task to run based on the criteria given in the
		   specification (tasks with the lowest priority value are ranked first,
		   and if multiple tasks have the lowest priority value, choose the
		   task with the lowest task ID).
		 - Subtract one from the chosen task's time requirement (since it is
		   being run for one step). If the task now requires 0 units of time,
		   it has finished, so remove it from the system and return its task ID.
		 - If the task did not finish, return SIMULATE_NONE_FINISHED.
	*/

//O(log(n))
	public int simulate(){
//makes t the top of the heap
		if(heapPriority[1] >=0){
		int t = heapPriority[1];

//subtracts one form time requirement
		heapObj[t].timeRequirement = (heapObj[t].timeRequirement - 1);

//if timeRequirement is zero kill that task and return that taskID
			if(heapObj[t].timeRequirement == 0){
				kill(heapObj[t].taskID);
				return heapObj[t].taskID;
			}
//if still time remaining continue the simulation
			else if(heapObj[t].timeRequirement >= 0){
				return SIMULATE_NONE_FINISHED;
			}
		}
//if no elements left end simulation
			return SIMULATE_IDLE;

}
}
