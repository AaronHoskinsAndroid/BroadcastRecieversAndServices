package examples.aaronhoskins.com.broadcastrecieversandservices;

public class ServiceMessageEvent {
    double randomNumber;

    public ServiceMessageEvent(double randomNumber) {
        this.randomNumber = randomNumber;
    }

    public double getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(double randomNumber) {
        this.randomNumber = randomNumber;
    }
}
