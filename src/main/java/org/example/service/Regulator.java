package org.example.service;

import java.util.List;

public interface Regulator {

    int setTemperatura(List<Float> outData);

    int getValuesTemperature();

    int deleteValuesTemperature();

    int adjustTemp(byte operation, float inData, List<Float> outData, int offsetOut);

    static Regulator create() {
        System.out.println("Регулятор начал работу.");
        return Holder.INSTANCE;
    }

    static void shutDown() {
        if (Holder.INSTANCE != null) {
            Holder.INSTANCE.deleteValuesTemperature();
            Holder.INSTANCE = null;
            System.out.println("Регулятор завершил работу.");
        } else {
            System.out.println("Регулятор уже выключен.");
        }
    }

    int getLastValues(int offsetOut);

    class Holder{
            private static Regulator INSTANCE = new VirtualRegulatorService();
        }
}
