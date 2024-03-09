public class ResizeObjectArray {
	public static Object[] resizeArray(Object[] oldArray, int newSize) {
		//implementation for any object type
		int oldSize = oldArray.length;
		Class<?> elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
		int preserveLength = Math.min(oldSize, newSize);

		if (preserveLength > 0) {
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		}

		return (Object[]) newArray;
	}
	
	public static Object[] resizeArray1(Object[] oldArray, int newSize) {
		//implementation for any object type
		int oldSize = oldArray.length;
		Class<?> elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
		int preserveLength = Math.min(oldSize, newSize);

		if (preserveLength > 0) {
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		}

		return (Object[]) newArray;
	}
	
}
