package org.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VirtualRegulator {

    private float inData; // – задаётся, до которой должен «нагреться» или «остыть» регулятор температуры.
    private final List<Float> outData; // - записываются предыдущие значения.
    private boolean running;

    VirtualRegulator() {
        this.outData = new ArrayList<>();
        this.running = true;
    }

    public float getInData() {
        return inData;
    }

    public void setInData(float inData) {
        this.inData = inData;
        this.outData.add(inData);
    }

    public List<Float> getOutData() {
        return outData;
    }

    public void setOutData() {
        outData.clear();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
        if (!running){
           setOutData();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        VirtualRegulator that = (VirtualRegulator) object;
        return Objects.equals(outData, that.outData);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(outData);
    }

    @Override
    public String toString() {
        return "VirtualRegulator{" +
                "outData=" + outData +
                ", running=" + running +
                '}';
    }
}