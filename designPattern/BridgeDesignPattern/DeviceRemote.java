package designPattern.BridgeDesignPattern;

interface Device{
    boolean isOn();
    void setVolume(int v);
    void setChannel(int v);
    int getVolume();
    int getChannel();
    void enable();
    void disable();
}

class Radio implements Device{
    private boolean isEnabled=false;
    private int vol=0, channel=0;

    @Override
    public boolean isOn() { return isEnabled;   }
    @Override
    public void setVolume(int v) { this.vol=v;}

    @Override
    public void setChannel(int c) { this.channel=c;}

    @Override
    public int getVolume() { return vol;}

    @Override
    public int getChannel() { return channel;}

    @Override
    public void enable() { isEnabled=true;}

    @Override
    public void disable() { if(isEnabled) isEnabled=false;} }

class TV implements Device{
    private boolean isEnabled=false;
    private int vol=0, channel=0;

    @Override
    public boolean isOn() { return isEnabled;   }
    @Override
    public void setVolume(int v) { this.vol=v;}

    @Override
    public void setChannel(int c) { this.channel=c;}

    @Override
    public int getVolume() { return vol;}

    @Override
    public int getChannel() { return channel;}

    @Override
    public void enable() { isEnabled=true;}

    @Override
    public void disable() { if(isEnabled) isEnabled=false;} }

interface Remote{
    void power();
    void volumeDown();
    void volumeUp();
    void channelDown();
    void channelUp();
}

class BasicRemote implements Remote{
    Device device;
    public BasicRemote(Device d){device=d;}

    @Override
    public void power() {
        if(device.isOn())
            device.disable();
        else device.enable();
    }

    @Override
    public void volumeDown() { device.setVolume(device.getVolume()-1); }

    @Override
    public void volumeUp() { device.setVolume(device.getVolume()+1);}

    @Override
    public void channelDown() {device.setChannel(device.getChannel()-1);}

    @Override
    public void channelUp() { device.setChannel(device.getChannel()+1);}
}

class AdvanceRemote implements Remote{
    Device device;
    public AdvanceRemote(Device d){device=d;}

    @Override
    public void power() {
        if(device.isOn())
            device.disable();
        else device.enable();
    }

    @Override
    public void volumeDown() { device.setVolume(device.getVolume()-1); }

    @Override
    public void volumeUp() { device.setVolume(device.getVolume()+1);}

    @Override
    public void channelDown() {device.setChannel(device.getChannel()-1);}

    @Override
    public void channelUp() { device.setChannel(device.getChannel()+1);}

    public void mute(){ device.setVolume(0);}
}

public class DeviceRemote {
    public static void main(String[] args)
    {
        TV tv =new TV();
        Radio radio=new Radio();

        BasicRemote basicRemote=new BasicRemote(radio);
        AdvanceRemote advanceRemote=new AdvanceRemote(tv);
        advanceRemote.volumeUp();
        System.out.println(tv.getVolume());

        advanceRemote.mute();
        System.out.println(tv.getVolume());
    }
}

