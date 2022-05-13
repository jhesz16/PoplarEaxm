import Pages.GlobalHelpers;
import Pages.PoplarPage;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class base {

    public static GlobalHelpers helpers;
    public static PoplarPage poplarPage;


    @DataProvider(name = "JustMeData")
    public Object[][] getData(Method fileName) {
        String path = "src\\test\\java\\TestData\\JustMeData";
        return createDPArray(path);
    }
    @DataProvider(name = "WithOthersData")
    public Object[][] test(Method fileName) {
        String path = "src\\test\\java\\TestData\\WithOthersData";
        return createDPArray(path);
    }
    @DataProvider(name = "NegativeTesting")
    public Object[][] negative(Method fileName) {
        String path = "src\\test\\java\\TestData\\NegativeTesting";
        return createDPArray(path);
    }
    private Object[][] createDPArray(String path)
    {
        List<String> arrayList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            arrayList = stream
                    .filter(s -> !s.contains("#"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object[][] array = new Object[arrayList.size()][];
        for (int i = 0; i < arrayList.size(); i++) {
            String row = arrayList.get(i);
            array[i] = Arrays.stream(row.split(",")).toArray();
        }
        return array;

    }

}
