package model;

public class AVLNode<K extends Comparable<K>, T> {

    private K key;
    private T value;
    private int height;
    private AVLNode<K, T> left;
    private AVLNode<K, T> right;

    public AVLNode(K key, T value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
        this.height = 1;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public AVLNode<K, T> getLeft() {
        return left;
    }

    public void setLeft(AVLNode<K, T> left) {
        this.left = left;
    }

    public AVLNode<K, T> getRight() {
        return right;
    }

    public void setRight(AVLNode<K, T> right) {
        this.right = right;
    }

}
