package org.firstinspires.ftc.teamcode.Commands;


<<<<<<< Updated upstream
import org.firstinspires.ftc.teamcode.Commands.Command;

=======
>>>>>>> Stashed changes
public class Wait extends Command {
        long time;
    long endTime;
    public Wait(long time){
                this.time = time;
    }
    public void start(){
        endTime = System.currentTimeMillis() + time;
    }
<<<<<<< Updated upstream
    public void execute(){

=======
    public boolean execute(){

        return false;
>>>>>>> Stashed changes
    }
    public void end(){
    }

    public boolean isFinished() {
        if (System.currentTimeMillis() >= endTime){
            return true;
        }
        else {
            return false;
        }
    }
}
