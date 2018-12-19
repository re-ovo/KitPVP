package cc.kinus.kitpvp.stats;

import cc.kinus.kitpvp.KitPVP;

import java.text.DecimalFormat;

public class PlayerStats {

    private String name;
    private String uuid;

    private int kills;
    private int deaths;
    private double kd;

    private int score;
    private int star;

    public PlayerStats(String name, String uuid, int kills, int deaths, double kd, int score, int star) {
        this.name = name;
        this.uuid = uuid;
        this.kills = kills;
        this.deaths = deaths;
        this.kd = kd;
        this.score = score;
        this.star = star;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public double getKd() {
        return kd;
    }

    public int getScore() {
        return score;
    }

    public int getStar() {
        return star;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setKd(double kd) {
        this.kd = kd;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public void update(){
        DecimalFormat df = new DecimalFormat("#.##");
        double kdr = 0.00D;
        if(deaths == 0 || kills == 0){
            kdr = 0;
        }else {
            kdr = ((double) kills) / ((double) deaths);
        }
        this.kd = Double.parseDouble(df.format(kdr));

        int add = kills * 5;
        int des = deaths * 3;

        this.score = add - des;
        if(this.score < 0){
            this.score = 0;
        }

        if(score < 50){
            star = 0;
            return;
        }
        if(score < 150){
            star = 1;
            return;
        }
        if(score < 300){
            star = 2;
            return;
        }
        if(star < 500){
            star = 3;
            return;
        }
        if(star < 1000){
            star = 4;
            return;
        }
        if(star < 1500){
            star = 5;
            return;
        }
        if(star < 2000){
            star = 6;
            return;
        }
        if(star < 3000){
            star = 7;
            return;
        }
        if(star < 4000){
            star = 8;
            return;
        }
        if(star < 5000){
            star = 9;
            return;
        }
        if(star < 6500){
            star = 10;
            return;
        }
        if(star < 8000){
            star = 11;
            return;
        }
        if(star < 10000){
            star = 12;
            return;
        }
    }

    public void addDeath(){
        this.deaths++;
        update();
    }

    public void addKills(){
        this.kills++;
        update();
    }

    @Override
    public String toString() {
        return "PlayerStats{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", kd=" + kd +
                ", score=" + score +
                ", star=" + star +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerStats that = (PlayerStats) o;

        if (kills != that.kills) return false;
        if (deaths != that.deaths) return false;
        if (Double.compare(that.kd, kd) != 0) return false;
        if (score != that.score) return false;
        if (star != that.star) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return uuid != null ? uuid.equals(that.uuid) : that.uuid == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + kills;
        result = 31 * result + deaths;
        temp = Double.doubleToLongBits(kd);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + score;
        result = 31 * result + star;
        return result;
    }
}
