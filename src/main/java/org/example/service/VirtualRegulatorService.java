package org.example.service;

import java.util.ArrayList;
import java.util.List;
public class VirtualRegulatorService implements Regulator {

    private static final float MAX_TEMPERATURE = 1000.0f;
    private static final float MIN_TEMPERATURE = -200.0f;
    private final List<Float> arrayOffsetOut = new ArrayList<>();

    private final VirtualRegulator virtualRegulator;

    public VirtualRegulatorService() {
        this.virtualRegulator = new VirtualRegulator();
    }

    @Override
    public int setTemperatura(List<Float> outData) {
        int temp2 = 0;
        if (virtualRegulator.isRunning()) {
            for (float temp : outData) {
               temp2 = checkingOnMinMaxTemperature(temp);
               if (temp2 != 0){
                   return temp2;
               }
            }
        }
        return temp2;
    }

    @Override
    public int getValuesTemperature() {
        if (virtualRegulator.getOutData().isEmpty()) {
            System.out.println(virtualRegulator.getOutData());
        return 4; // Ошибка: нет значений для получения
        } else {
            System.out.println(virtualRegulator.getOutData());
            return 0;
        }
    }

    @Override
    public int deleteValuesTemperature(){
        if (virtualRegulator.isRunning()){
            virtualRegulator.setRunning(false);
            return 0;
        } else
            return 8;
    }

    @Override
    public int adjustTemp(byte operation, float inData, List<Float> outData, int offsetOut) throws NullPointerException{
        int readCount = (operation >> 4) & 0b00001111; // Получаем 4-7 биты

        // Операция очистки
        if ((operation & 0b10000000) != 0) {
            deleteValuesTemperature();
        }

        // Добавление новых значений
        if ((operation & 0b01000000) != 0) {
            setTemperatura(outData);
        }

        // Операция получения значений температуры
        if ((operation & 0b00100000) != 0) {
            getValuesTemperature();
        }
        // Операция получения последних значений температуры
        if ((operation & 0b00010000) != 0) {
            getLastValues(offsetOut);
        }

        // Операция задания температуры
        if ((readCount & 0b00000100) != 0) {
            checkingOnMinMaxTemperature(inData);
        }

        // Проверка резервированного бита
        if ((readCount & 0b00000001) != 0) {
            return 6; // Ошибка: резервированный бит не равен 1
        }
        return 0;
    }

    private int checkingOnMinMaxTemperature(float inData){
        if (inData < MIN_TEMPERATURE) {
            return 3; // Ошибка: слишком низкая температура
        }
        if (inData > MAX_TEMPERATURE) {
            return 5; // Ошибка: слишком высокая температура
        }
         virtualRegulator.setInData(inData);
        return 0;
    }

    @Override
    public int getLastValues(int offsetOut){ // печать последних 3 значений
       List<Float> outData = virtualRegulator.getOutData();

       if (offsetOut < outData.size()) {
           for (int i = outData.size() - 1; i >= outData.size() - offsetOut; i--) {
               arrayOffsetOut.add(outData.get(i));
           }
           return 0;
       } else
           return 7;
    }
}
