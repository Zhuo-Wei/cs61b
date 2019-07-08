public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static final double g = 6.67e-11;

    public Planet(String imgFileName0){
        imgFileName = imgFileName0;
    }
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p){
        this.imgFileName = p.imgFileName;
        this.mass = p.mass;
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
    }

    public double calcDistance(Planet p){
        double dx = this.xxPos - p.xxPos;
        double dy = this.yyPos - p.yyPos;
        double r = Math.sqrt(dx * dx + dy * dy);
        return r;
    }

    /**
     *
     * @param p
     * @return the force exerted on this planet by the given planet
     */
    public double calcForceExertedBy(Planet p){
        double f = g * this.mass * p.mass / (this.calcDistance(p) * this.calcDistance(p));
        return f;
    }

    public double calcForceExertedByX(Planet p){
        double dx = p.xxPos - this.xxPos;
        double fx = this.calcForceExertedBy(p) * dx / this.calcDistance(p);
        return fx;
    }
    public double calcForceExertedByY(Planet p) {
        double dy = p.yyPos - this.yyPos;
        double fy = this.calcForceExertedBy(p) * dy / this.calcDistance(p);
        return fy;
    }

    public double calcNetForceExertedByX(Planet[] allP){
        double fxNet = 0;
        for(int i = 0; i < allP.length ; i++) {
            if(!this.equals(allP[i])) {
                fxNet = fxNet + this.calcForceExertedByX(allP[i]);
            }
        }
        return fxNet;
    }
    public double calcNetForceExertedByY(Planet[] allP) {
        double fyNet = 0;
        for(int i = 0; i < allP.length ; i++) {
            if(!this.equals(allP[i])) {
                fyNet = fyNet + this.calcForceExertedByY(allP[i]);
            }
        }
        return fyNet;
    }

    /**
     *
     * @param dt small period
     * @param fx force on x
     * @param fy force on y
     *
     */
    public void update(double dt, double fx, double fy){
        double ax = fx/this.mass;
        double ay = fy/this.mass;
        this.xxVel = this.xxVel + dt * ax;
        this.yyVel = this.yyVel + dt * ay;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + imgFileName);
    }


}
