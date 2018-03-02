public class nodeobj{
public int time;
public int taskID;
public int timeRequirement;
public int priority;

public nodeobj(int taskID, int time, int timeRequirement, int priority){
  this.taskID = taskID;
  this.time = time;
  this.timeRequirement = timeRequirement;
  this.priority = priority;
}
}
