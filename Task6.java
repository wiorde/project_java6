import java.util.Arrays;
import java.util.Objects;
public class Task6 {
    public static void main(String[] args) {
        LicensePlate p1=new LicensePlate("A123BC","RU");
        LicensePlate p2=new LicensePlate("E456DF","RU");
        Car car1=new Car("Toyota",180,FuelType.PETROL,p1);
        Car car2=new Car("Tesla",250,FuelType.ELECTRIC,p2);
        Bicycle bike=new Bicycle("Stels",25);
        Transport[] garage={car1,car2,bike};
        System.out.println("Информация о транспорте");
        for (Transport t:garage) {
            System.out.println(t.toString());
            System.out.println("Двигатель: "+getEngineInfo(t));
        }
        System.out.println("Сортировка по скорости");
        Arrays.sort(garage);
        for (Transport t:garage) {
            System.out.println(t.toString());
        }
        System.out.println("Тест исключения");
        try {
            LicensePlate badPlate=new LicensePlate(null,"RU");
            Car badCar=new Car("Lada",120,FuelType.PETROL,badPlate);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: "+e.getMessage());
        } finally {
            System.out.println("Проверка завершена.");
        }
    }
    
    static String getEngineInfo(Transport t) {
        if (t instanceof Car) {
            Car c=(Car) t;
            switch (c.getFuel()) {
                case PETROL:
                    return "Бензиновый";
                case ELECTRIC:
                    return "Электрический";
                case DIESEL:
                    return "Дизельный";
                default:
                    return "Неизвестно";
            }
        }
        return "Нет двигателя";
    }
}

enum FuelType {
    PETROL("Бензин"),
    DIESEL("Дизель"),
    ELECTRIC("Электричество");
    private final String description;
    FuelType(String description) {
        this.description=description;
    }
    public String getDescription() {
        return description;
    }
}
record LicensePlate(String number,String region) implements Comparable<LicensePlate> {
    public LicensePlate {
        Objects.requireNonNull(number,"Номер не может быть null");
        if (number.isBlank()) {
            throw new IllegalArgumentException("Номер не может быть пустым");
        }
    }
    @Override
    public int compareTo(LicensePlate other) {
        return this.number.compareTo(other.number);
    }
}

sealed abstract class Transport implements Comparable<Transport> permits Car,Bicycle {
    private String name;
    private int maxSpeed;
    public Transport(String name,int maxSpeed) {
        this.name=name;
        this.maxSpeed=maxSpeed;
    }
    public String getName() {
        return name;
    }
    public int getMaxSpeed() {
        return maxSpeed;
    }
    @Override
    public int compareTo(Transport other) {
        return Integer.compare(this.maxSpeed,other.maxSpeed);
    }
    public abstract String getDescription();
}

final class Car extends Transport {
    private FuelType fuel;
    private LicensePlate plate;
    public Car(String name,int maxSpeed,FuelType fuel,LicensePlate plate) {
        super(name,maxSpeed);
        this.fuel=fuel;
        this.plate=plate;
    }
    public FuelType getFuel() {
        return fuel;
    }
    @Override
    public String getDescription() {
        return "Машина: "+getName()+" (Номер: "+plate.number()+")";
    }
    @Override
    public String toString() {
        return getDescription();
    }
}
final class Bicycle extends Transport {
    public Bicycle(String name,int maxSpeed) {
        super(name,maxSpeed);
    }
    @Override
    public String getDescription() {
        return "Велосипед: "+getName();
    }
    @Override
    public String toString() {
        return getDescription();
    }
}