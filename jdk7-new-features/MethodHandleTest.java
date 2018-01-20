import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by lzyer on 2017/8/27.
 */
public class MethodHandleTest {
    public MethodHandleTest(){
        System.out.println("constructor...");
    }
    public String print0() {
        return "print-000...";
    }
    public void print1(){
        System.out.println("print-1111");
    }
    public String print2(String str){
        return "print-"+str;
    }
    public void print3(String str){
        System.out.println("print-"+str);
    }

    public static void printStatic1(){
        System.out.println("printStatic11111");
    }
    public static String printStatic2(){
        return "printStatic2222";
    }
    public static void printStatic3(String str){
        System.out.println("printStatic3-"+str);
    }
    public static String printStatic4(String str){
        return "printStatic4-"+str;
    }
    public static void main(String[] args) {
        MethodHandleTest mht = new MethodHandleTest();
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            //调用构造方法
            MethodHandle handle = lookup.findConstructor(MethodHandleTest.class, MethodType.methodType(void.class));
            handle.invoke();
            //0;
            handle = lookup.findVirtual(MethodHandleTest.class, "print0", MethodType.methodType(String.class));
            System.out.println((String)handle.invokeExact(mht));
            //1;
            handle = lookup.findVirtual(MethodHandleTest.class, "print1", MethodType.methodType(void.class));
            handle.invokeExact(mht);

            //2;
            handle = lookup.findVirtual(MethodHandleTest.class, "print2", MethodType.methodType(String.class, String.class));
            System.out.println((String)handle.invokeExact(mht, "hello2"));

            //3;
            handle = lookup.findVirtual(MethodHandleTest.class, "print3", MethodType.methodType(void.class,String.class));
            handle.invoke(mht, "hello3");
            System.out.println("================");
            //0;
            handle = lookup.findSpecial(MethodHandleTest.class, "print0", MethodType.methodType(String.class), MethodHandleTest.class);
            System.out.println((String)handle.invokeExact(mht));

            //1;
            handle = lookup.findSpecial(MethodHandleTest.class, "print1", MethodType.methodType(void.class), MethodHandleTest.class);
            handle.invokeExact(mht);

            //2;
            handle = lookup.findSpecial(MethodHandleTest.class, "print2", MethodType.methodType(String.class, String.class), MethodHandleTest.class);
            System.out.println((String)handle.invokeExact(mht, "hello2"));

            //3;
            handle = lookup.findSpecial(MethodHandleTest.class, "print3", MethodType.methodType(void.class,String.class), MethodHandleTest.class);
            handle.invoke(mht, "hello3");
            System.out.println("================");
            handle = lookup.findStatic(MethodHandleTest.class, "printStatic1", MethodType.methodType(void.class));
            handle.invoke();
            handle = lookup.findStatic(MethodHandleTest.class, "printStatic2", MethodType.methodType(String.class));
            System.out.println((String)handle.invoke());

            handle = lookup.findStatic(MethodHandleTest.class, "printStatic3", MethodType.methodType(void.class, String.class));
            handle.invoke("hello3");

            handle = lookup.findStatic(MethodHandleTest.class, "printStatic4", MethodType.methodType(String.class, String.class));
            System.out.println((String)handle.invokeExact("hello3"));

        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
