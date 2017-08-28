
import java.util.ArrayList;
import java.util.List;

public class StreamDemo
{
	public static void main(String[] args)
	{
		List<String> ls = new ArrayList<String>();
		ls.add("adsa");
		ls.add("fsd");
		ls.add("adcb");
		ls.add("nbc");
		ls.add("rtyrt");
		ls.add("xvb");
		ls.add("qwe");
		//java 8
		// Optional<String> max = ls.stream().max(String::compareTo);
		// System.out.println(max.get());
	
		// ls.stream().sorted().forEach(e->System.out.println(e));
		
		//ls.stream().distinct().count();去除重复的个数
	}
}
