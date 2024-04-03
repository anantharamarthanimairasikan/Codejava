package CollectionandGenericAssignment;

import java.util.List;
import java.util.Vector;

import Assignment.InvalidRentalPeriodException;

public class ArthimaticOperations {
	public static <T extends Number> Number add(T t1, T t2) {
		return (t1.doubleValue() + t2.doubleValue());
	}

	public static <T extends Number> Number sub(T t1, T t2) {
		return (t1.doubleValue() - t2.doubleValue());
	}

	public static <T extends Number> Number div(T t1, T t2) {
		return (t1.doubleValue() / t2.doubleValue());
	}

	public static <T extends Number> Number add(List<? extends Number> list) {
		double d = 0;
		;
		for (int i = 0; i < list.size(); i++)
			d += list.get(i).doubleValue();

		return new Double(d);
	}
	public static <T extends Number> Number sub(List<? extends Number> list) {
		double d = list.get(0).doubleValue();
		for (int i = 1; i < list.size(); i++)
			d -= list.get(i).doubleValue();

		return new Double(d);
	}
	
	public static <T extends Number> Number div(List<? extends Number> list) throws Exception {
		double d = list.get(0).doubleValue();
		
		for (int i = 1; i < list.size(); i++)
			if(list.get(i).doubleValue()==0) {
				throw new Exception("Invalid Number.");
			}else {
				d /= list.get(i).doubleValue();
			}
			

		return new Double(d);
	}

	public static void dumpList(List<?> list) {
		System.out.println("List dump with unbounded wildcard:");
		for (int i = 0; i < list.size(); i++)
			System.out.println(list.get(i));
	}

	public static void main(String[] args) throws Exception {

		Integer i1 = new Integer(34), i2 = new Integer(43);
		System.out.println("Add with generic method: " + ArthimaticOperations.add(i1, i2));
		Float f1 = new Float(12.56), f2 = new Float(3.6778);
		System.out.println("Add with generic method: " + ArthimaticOperations.add(f1, f2));
		System.out.println("Subtraction with generic method: "+ ArthimaticOperations.sub(f1, f2));
		System.out.println("Division with generic method: "+ArthimaticOperations.div(f1, f2));
		
	
		Vector<Number> l = new Vector<Number>();
		l.add(new Integer(34));
		l.add(new Integer(43));

		System.out.println("Add with upper bounded wildcard: " + ArthimaticOperations.add(l));
		System.out.println("Add with upper bounded wildcard: " + ArthimaticOperations.sub(l));
		System.out.println("Add with upper bounded wildcard: " + ArthimaticOperations.div(l));

		ArthimaticOperations.dumpList(l);
	}
}
