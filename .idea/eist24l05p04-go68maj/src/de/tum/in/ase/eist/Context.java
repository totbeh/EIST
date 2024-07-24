package de.tum.in.ase.eist;

import java.util.List;

public class Context {
    private List<Chapter> book;
    private SearchStrategy searchAlgorithm;

    public Context(List<Chapter> book, SearchStrategy searchAlgorithm) {
        this.book = book;
        this.searchAlgorithm = searchAlgorithm;
    }

    public Context() {
    }

    public SearchStrategy getSearchAlgorithm() {
        return searchAlgorithm;
    }

    public void setSearchAlgorithm(SearchStrategy searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
    }

    public int search(String string) {
        return searchAlgorithm.performSearch(book, string);
    }

    public boolean isChaptersSortedByName() {
        for (int i = 0; i + 1 < book.size(); i++) {
            if (book.get(i).getName().compareTo(book.get(i + 1).getName()) > 0) {
                return false;
            }
        }
        return true;
    }

    public List<Chapter> getBook() {
        return book;
    }

    public void setBook(List<Chapter> book) {
        this.book = book;
    }
}
