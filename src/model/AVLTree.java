package model;

import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<K extends Comparable<K>, T> implements IAVLTree<K, T> {

    private AVLNode<K, T> root;

    public AVLTree() {
        this.root = null;
    }

    @Override
    public T search(K key) {
        AVLNode<K, T> node = searchNode(key, root);
        return node != null ? node.getValue() : null;
    }

    private AVLNode<K, T> searchNode(K key, AVLNode<K, T> node) {
        if (node == null) {
            return null;
        }
        if (node.getKey().equals(key)) {
            return node;
        }
        if (key.compareTo(node.getKey()) < 0) {
            return searchNode(key, node.getLeft());
        } else {
            return searchNode(key, node.getRight());
        }
    }

    @Override
    public void insert(K key, T value) {
        AVLNode<K, T> node = new AVLNode<>(key, value);
        if (root == null) {
            root = node;
        } else {
            root = insert(root, node);
        }
    }

    private AVLNode<K, T> insert(AVLNode<K, T> currentNode, AVLNode<K, T> newNode) {
        if (currentNode == null) {
            return newNode;
        }

        if (newNode.getKey().compareTo(currentNode.getKey()) < 0) {
            currentNode.setLeft(insert(currentNode.getLeft(), newNode));
        } else if (newNode.getKey().compareTo(currentNode.getKey()) > 0) {
            currentNode.setRight(insert(currentNode.getRight(), newNode));
        } else {
            // Duplicated key
            return currentNode;
        }

        // Actualizar altura del nodo
        updateHeight(currentNode);

        // Balancear el árbol si es necesario
        int balanceFactor = getBalanceFactor(currentNode);

        // Left Case
        if (balanceFactor > 1 && currentNode.getLeft() != null && newNode.getKey().compareTo(currentNode.getLeft().getKey()) <= 0) {
            return rightRotate(currentNode);
        }

        // Right Case
        if (balanceFactor < -1 && currentNode.getRight() != null && newNode.getKey().compareTo(currentNode.getRight().getKey()) >= 0) {
            return leftRotate(currentNode);
        }

        // Left Right Case
        if (balanceFactor > 1 && currentNode.getLeft() != null && newNode.getKey().compareTo(currentNode.getLeft().getKey()) > 0) {
            currentNode.setLeft(leftRotate(currentNode.getLeft()));
            return rightRotate(currentNode);
        }

        // Right Left Case
        if (balanceFactor < -1 && currentNode.getRight() != null && newNode.getKey().compareTo(currentNode.getRight().getKey()) < 0) {
            currentNode.setRight(rightRotate(currentNode.getRight()));
            return leftRotate(currentNode);
        }
        return currentNode;
    }


    private void updateHeight(AVLNode<K, T> node) {
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
    }


    private int getHeight(AVLNode<K, T> node) {
        if (node == null) {
            return 0;
        }
        return node.getHeight();
    }

    private int getBalanceFactor(AVLNode<K, T> node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    @Override
    public T delete(K key) {
        root = delete(root, key);
        return root == null ? null : root.getValue();
    }

    private AVLNode<K, T> delete(AVLNode<K, T> node, K key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.getKey()) < 0) {
            node.setLeft(delete(node.getLeft(), key));
        } else if (key.compareTo(node.getKey()) > 0) {
            node.setRight(delete(node.getRight(), key));
        } else { // Encontramos el nodo a eliminar
            if (node.getLeft() == null && node.getRight() == null) { // Nodo hoja
                node = null;
            } else if (node.getLeft() == null) { // Nodo con un hijo derecho
                node = node.getRight();
            } else if (node.getRight() == null) { // Nodo con un hijo izquierdo
                node = node.getLeft();
            } else { // Nodo con dos hijos
                AVLNode<K, T> minValueNode = findMinNode(node.getRight());
                node.setKey(minValueNode.getKey());
                node.setValue(minValueNode.getValue());
                node.setRight(delete(node.getRight(), minValueNode.getKey()));
            }
        }

        if (node == null) {
            return null;
        }

        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);

        // Balancear el árbol si es necesario
        if (balanceFactor > 1 && getBalanceFactor(node.getLeft()) >= 0) {
            node = rightRotate(node);
        }
        if (balanceFactor < -1 && getBalanceFactor(node.getRight()) <= 0) {
            node = leftRotate(node);
        }
        if (balanceFactor > 1 && getBalanceFactor(node.getLeft()) > 0) {
            node.setLeft(leftRotate(node.getLeft()));
            node = rightRotate(node);
        }
        if (balanceFactor < -1 && getBalanceFactor(node.getRight()) < 0) {
            node.setRight(rightRotate(node.getRight()));
            node = leftRotate(node);
        }

        return node;
    }


    // ------------------------ROTACIONES------------------------
    private AVLNode<K, T> rightRotate(AVLNode<K, T> node) {
        AVLNode<K, T> leftChild = node.getLeft();
        AVLNode<K, T> leftRightChild = leftChild.getRight();

        // Rotación
        leftChild.setRight(node);
        node.setLeft(leftRightChild);

        // Actualizar altura de los nodos
        updateHeight(node);
        updateHeight(leftChild);

        // Actualizar la raíz si es necesario
        if (root == node) {
            root = leftChild;
        }

        return leftChild;
    }

    private AVLNode<K, T> leftRotate(AVLNode<K, T> node) {
        AVLNode<K, T> rightChild = node.getRight();
        AVLNode<K, T> rightLeftChild = rightChild.getLeft();

        // Rotación
        rightChild.setLeft(node);
        node.setRight(rightLeftChild);

        // Actualizar altura de los nodos
        updateHeight(node);
        updateHeight(rightChild);

        // Actualizar la raíz si es necesario
        if (root == node) {
            root = rightChild;
        }

        return rightChild;
    }


    // ------------------------MIN AND MAX NODES------------------------
    private AVLNode<K, T> findMinNode(AVLNode<K, T> node) {
        if (node.getLeft() == null) {
            return node;
        }
        return findMinNode(node.getLeft());
    }

    private AVLNode<K, T> findMaxNode(AVLNode<K, T> node) {
        if (node.getRight() == null) {
            return node;
        }
        return findMaxNode(node.getRight());
    }

    // ------------------------RECORRIDOS------------------------
    @Override
    public String inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(root, sb);
        return sb.toString();
    }

    private void inOrder(AVLNode<K, T> node, StringBuilder sb) {
        if (node != null) {
            inOrder(node.getLeft(), sb);
            sb.append(node.getKey().toString()).append(" ");
            if (node == findMaxNode(root)) {
                sb.deleteCharAt(sb.length() - 1);
            }
            inOrder(node.getRight(), sb);
        }
    }

    @Override
    public String preOrder() {
        StringBuilder sb = new StringBuilder();
        preOrder(root, sb);
        return sb.toString();
    }

    private void preOrder(AVLNode<K, T> node, StringBuilder sb) {
        if (node != null) {
            sb.append(node.getKey().toString()).append(" ");
            preOrder(node.getLeft(), sb);
            if (node == findMaxNode(root)) {
                sb.deleteCharAt(sb.length() - 1);
            }
            preOrder(node.getRight(), sb);
        }
    }

    public void levelOrder(){

        if (root == null) return;

        Queue<AVLNode<K,T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            AVLNode<K,T> node = queue.poll();
            System.out.print(node.getKey() + " ");

            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }

            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }

        System.out.println();

    }


}
