package pl.sda.intermediate11;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair<T, E> {
    private T obj1;
    private E obj2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (obj1 != null ? !obj1.equals(pair.obj1) : pair.obj1 != null) return false;
        return obj2 != null ? obj2.equals(pair.obj2) : pair.obj2 == null;
    }

    @Override
    public int hashCode() {
        int result = obj1 != null ? obj1.hashCode() : 0;
        result = 31 * result + (obj2 != null ? obj2.hashCode() : 0);
        return result;
    }
}
