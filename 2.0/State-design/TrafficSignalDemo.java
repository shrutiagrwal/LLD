package designPatterns;
//state
interface SignalState{
    void action(TrafficLight context);
}

class RedSignal implements SignalState{
    @Override
    public void action(TrafficLight context) {
        System.out.println("changing colour to red light");
        context.setState(new GreenSignal());
    }
}

class YellowSignal implements SignalState{
    @Override
    public void action(TrafficLight context) {
        System.out.println("changing colour to Yellow light");
        context.setState(new RedSignal());
    }
}

class GreenSignal implements SignalState{
    @Override
    public void action(TrafficLight context) {
        System.out.println("changing colour to Green light");
        context.setState(new YellowSignal());
    }
}
// product
class TrafficLight {
    SignalState trafficSignalState;
    TrafficLight(){
        this.trafficSignalState =new RedSignal();
    }

    public void setState(SignalState state){
        this.trafficSignalState =state;
    }
    public void change(){
        this.trafficSignalState.action(this);
    }
}

public class TrafficSignalDemo {
    static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();
        trafficLight.change();
        trafficLight.change();
        trafficLight.change();
    }
}
