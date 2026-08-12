import java.util.ArrayList;
import java.util.List;
interface Observer{
    void update();
}

class Subscriber implements Observer{
    private final YoutubeChannel channel;

    public Subscriber(YoutubeChannel channel){
        this.channel=channel;
    }
    @Override
    public void update() {
        System.out.println("New video published is:" + channel.getLatestVideo() );
    }
}

class YoutubeChannel{
    String name;
    List<String> videos= new ArrayList<>();
    List<Observer>subscribers =new ArrayList<>();
    YoutubeChannel(String name){
        this.name=name;
    }
    void addVideo(String video){
        videos.add(video);
        notifySubscribers();
    }
     void removeVideo(String video){
        videos.remove(video);
     }
     void addSubscriber(Observer observer){
        subscribers.add(observer);
     }
     void removeSubscriber(Observer observer){
        subscribers.remove(observer);
     }
     private void notifySubscribers(){
        for(Observer subscriber: subscribers){
            subscriber.update();
        }
     }
     String getLatestVideo(){
        return videos.getLast();
     }
}

public class ObserverPull {
    public static void main(String[] args) {
        YoutubeChannel channel=new YoutubeChannel("T-series");
        channel.addVideo("song1");
        Subscriber subscriber1=new Subscriber(channel);
        channel.addSubscriber(subscriber1);
        channel.addVideo("song2");
        Subscriber subscriber2= new Subscriber(channel);
        channel.addSubscriber(subscriber2);
        channel.addVideo("song3");
        channel.removeSubscriber(subscriber1);
        channel.addVideo("song3");
    }
}
