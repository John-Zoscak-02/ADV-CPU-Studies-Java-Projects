public class Book implements Comparable {
    private String author;
    private String country;
    private String imageLink;
    private String language;
    private String link;
    private int pages;
    private String title;
    private int year;

    public Book() {
        super();
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", country='" + country + '\'' +
                ", year=" + year +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCountry() { return country; }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLink() { return link; }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(Object o) {
        if ( o.getClass() != this.getClass() ) {
            return Integer.MAX_VALUE;
        }

        Book other = ( Book )o;
        if ( !other.getCountry().equals( getCountry() ) ) {
            return getCountry().compareTo( other.getCountry() );
        }
        else if ( other.getYear() != getYear() ) {
            return getYear() - other.getYear();
        }
        else if ( !other.getTitle().equals( getTitle() ) ) {
            return getTitle().compareTo( other.getTitle() );
        }
        else {
            return 0;
        }
    }
}
