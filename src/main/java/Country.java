public class Country {
    private String country;
    private String city;
    private int population;

    public Country(String country, String city, int population) {
        this.country = country;
        this.city = city;
        this.population = population;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "Country{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", population=" + population +
                '}';
    }
}
