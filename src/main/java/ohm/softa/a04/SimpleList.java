package ohm.softa.a04;

import java.util.function.Function;

public interface SimpleList<T> extends Iterable<T> {

	/**
	 * Add a given object to the back of the list.
	 */
	void add(T o);

	/**
	 * @return current size of the list
	 */
	int size();

	/**
	 * Generate a new list using the given filter instance.
	 * @return a new, filtered list
	 */

	@SuppressWarnings("unchecked")
	default void addDefault(Class<T> clazz) {
		try {
			/* better solution would be to use Google Guava to get a type token
			and use this token to create a new instance
			because we didn't include a reference to Guava this is the easiest way to create new instance of T */
			this.add(clazz.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	default public SimpleList<T> filter(SimpleFilter<T> filter){
		SimpleList<T> result = new SimpleListImpl<T>();
		for(T o : this){
			if(filter.include(o)){
				result.add(o);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	default <R> SimpleList<R> map(Function<T, R> transform) {
		SimpleList<R> result;
		try {
			result = (SimpleList<R>) this.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			result = new SimpleListImpl<>();
		}
		for(T t : this){
			result.add(transform.apply(t));
		}
		return result;
	}
}
