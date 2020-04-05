package com.snakesandladders.game.elements;

import com.snakesandladders.game.errors.GameException;

import java.util.Arrays;
import java.util.HashSet;

public class PlayerGroup {

    private CircularLinkedList<Player> players;

    public PlayerGroup(Player... players) {
        this.players = new CircularLinkedList<>(players);
        validateGroup(players);
    }

    public int size() {
        return players.size;
    }

    public Player currentPlayer() {
        return players.element();
    }

    private void validateGroup(Player[] players) {
        if (players.length < 2) throw new GameException("Expected at least two unique players in the group!");

        if (new HashSet<>(Arrays.asList(players)).size() < players.length) {
            throw new GameException("Same player cannot register multiple times!");
        }
    }

    public Player moveToNextPlayer() {
        return this.players.nextElement();
    }

    private static class CircularLinkedList<T> {

        Node<T> first;
        Node<T> last;
        Node<T> curr;
        Integer size = 0;

        CircularLinkedList(T... elements) {
            Node<T> currNode, prevNode = null;

            for (T element : elements) {
                currNode = new Node<>(element);

                if (first == null) first = currNode;
                if (prevNode != null) prevNode.nextNode = currNode;
                size++;

                last = currNode;
                prevNode = currNode;
            }

            curr = first;
            last.nextNode = first;
        }

        T nextElement() {
            curr = curr.nextNode;
            return curr.element;
        }

        public T element() {
            return curr.element;
        }
    }

    private static class Node<T> {

        Node<T> nextNode;
        T element;

        Node(T element) {
            this.element = element;
        }

    }

}
