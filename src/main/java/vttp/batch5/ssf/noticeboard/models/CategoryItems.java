package vttp.batch5.ssf.noticeboard.models;

public class CategoryItems {

    private String publicNotice;
    private String sport;
    private String meeting;
    private String garageSale;
    private String others;
    
    public String getPublicNotice() {
        return publicNotice;
    }
    public void setPublicNotice(String publicNotice) {
        this.publicNotice = publicNotice;
    }
    public String getSport() {
        return sport;
    }
    public void setSport(String sport) {
        this.sport = sport;
    }
    public String getMeeting() {
        return meeting;
    }
    public void setMeeting(String meeting) {
        this.meeting = meeting;
    }
    public String getGarageSale() {
        return garageSale;
    }
    public void setGarageSale(String garageSale) {
        this.garageSale = garageSale;
    }
    public String getOthers() {
        return others;
    }
    public void setOthers(String others) {
        this.others = others;
    }

    @Override
    public String toString() {
        return "categoryItems [publicNotice=" + publicNotice + ", sport=" + sport + ", meeting=" + meeting
                + ", garageSale=" + garageSale + ", others=" + others + "]";
    }

    

    
    
}
