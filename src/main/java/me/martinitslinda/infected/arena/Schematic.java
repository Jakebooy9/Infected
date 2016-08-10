package me.martinitslinda.infected.arena;

public class Schematic{

    private byte[] blocks;
    private byte[] data;
    private short width;
    private short length;
    private short height;

    public Schematic(byte[] blocks, byte[] data, short width, short length, short height){
        this.blocks=blocks;
        this.data=data;
        this.width=width;
        this.length=length;
        this.height=height;
    }

    /**
     * @return the blocks
     */
    public byte[] getBlocks(){
        return blocks;
    }

    /**
     * @return the data
     */
    public byte[] getData(){
        return data;
    }

    /**
     * @return the width
     */
    public short getWidth(){
        return width;
    }

    /**
     * @return the length
     */
    public short getLength(){
        return length;
    }

    /**
     * @return the height
     */
    public short getHeight(){
        return height;
    }


}
