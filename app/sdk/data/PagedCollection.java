package sdk.data;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

public class PagedCollection<E> implements Collection<E> {
    private Collection<E> elementData;
    private int totalAvailableRecords;
    private int pageSize;

    private PagedCollection() {}

    public PagedCollection(Collection<E> elementData, int pageSize, int totalAvailableRecords) {
        this.elementData = elementData;
        this.setPageSize(pageSize);
        this.setTotalAvailableRecords(totalAvailableRecords);
    }

    @Override
    public int size() {
        return elementData.size();
    }

    @Override
    public boolean isEmpty() {
        return elementData.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return elementData.contains(o);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return elementData.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return elementData.toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return elementData.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return elementData.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return elementData.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return elementData.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        return elementData.addAll(c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return elementData.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return elementData.retainAll(c);
    }

    @Override
    public void clear() {
        elementData = null;
    }

    public Collection<E> getElementData() {
        return elementData;
    }

    public void setElementData(Collection<E> elementData) {
        this.elementData = elementData;
    }

    public int getTotalAvailableRecords() {
        return totalAvailableRecords;
    }

    public void setTotalAvailableRecords(int totalAvailableRecords) {
        this.totalAvailableRecords = totalAvailableRecords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
