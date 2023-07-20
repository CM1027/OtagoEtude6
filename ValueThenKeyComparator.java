import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

//https://stackoverflow.com/questions/3074154/sorting-a-hashmap-based-on-value-then-key

public class ValueThenKeyComparator<K extends Comparable<? super K>,
                                    V extends Comparable<? super V>>
    implements Comparator<Map.Entry<K, V>> {

    public int compare(Map.Entry<K, V> a, Map.Entry<K, V> b) {
        int cmp1 = b.getValue().compareTo(a.getValue());
        if (cmp1 != 0) {
            return cmp1;
        } else {
            return a.getKey().compareTo(b.getKey());
        }
    }

}