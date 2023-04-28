package model;

public interface IAVLTree<K, T> {

    public T search(K key);
    public void insert(K key, T value);
    public T delete(K key);
    public String inOrder();
    public String preOrder();

}
