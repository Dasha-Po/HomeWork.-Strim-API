import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Домашнее задание по теме «Stream API. Потоки, повторные вызовы, основные методы»
public class Main {
    public static void main(String[] args) {
        //создание коллекции из 10 000 000 людей(рандомно)
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        //Найти количество несовершеннолетних (т.е. людей младше 18 лет).
        long countMinor = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + countMinor);
        //Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
        List<String> conscripts = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .flatMap(person -> Stream.of(person.getFamily()))
                .limit(5)
                .collect(Collectors.toList());
        System.out.println(conscripts);
        //Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием
        // в выборке (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин).
        Collection<Person> workables = persons.stream()
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getSex() == Sex.MAN ? person.getAge() <= 65 : person.getAge() <= 60)
                .sorted(Comparator.comparing(Person :: getFamily))
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("Список потенциально работоспособных людей: " + workables);
    }


}
