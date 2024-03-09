import java.util.Random;


public class ShuffleArrayInPlace {
	

	public static void shuffle1(int[] a) {
		int length = a.length;
		
		Random random = new Random();
		random.nextInt();
		
		for(int i = 0; i < length; i++) {
			int j = i + random.nextInt(length-i);
			
			//Swap
			int tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}
	
	public static void shuffle2(int[] a) {
		Random random = new Random();
		random.nextInt();
		
		for(int i = a.length-1; i >= 1; i--) {
			int j = random.nextInt(i+1);
			
			//Swap
			int tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}
	
	public static <T> void shuffle3(T[] a) {
		int length = a.length;
				
		Random random = new Random();
		random.nextInt();
				
		for(int i = 0; i < length; i++) {
			int j = i + random.nextInt(length-i);
			
			//Swap
			T tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}

