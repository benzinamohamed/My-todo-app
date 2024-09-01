package TaskOB;

public class TaskCL {
    private String taskname;
    private int id ;
    private int status;

    public TaskCL(String taskname, int status, int id) {
        this.taskname = taskname;
        this.status = status;
        this.id = id;
    }

    public TaskCL(String taskname, int status) {
        this.taskname = taskname;
        this.status = status;
    }

    public TaskCL() {

    }

    public String getTaskname() {
        return taskname;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public void setId(int id) {
        this.id = id;
    }
}
