package com.main.app.map;

import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class StaticMapURL {

    private String path;

    public StaticMapURL(String path) {
        this.path = path;
    }

    public StaticMapURL setCenter(LatLng latLng) {
        this.path += "center=" + latLng.lat + "," + latLng.lng + "&";
        return this;
    }

    public StaticMapURL addPoly(final LatLng latLng, double distance) {
        List<LatLng> latLngList = new ArrayList<>();
//        latLngList.add(new LatLng(49.763407d, 18.867016d));
//        latLngList.add(new LatLng(49.762496d, 18.869143d));
//        latLngList.add(new LatLng(49.762038d, 18.867788d));
//        latLngList.add(new LatLng(49.763407d, 18.867016d));
        final int STEPS = 18;
        final float STEP_ANGLE = 360 / STEPS;
        float angle = 0;
        for (int i = 0; i <= STEPS; i++) {
            latLngList.add(SphericalUtil.computeOffset(latLng, distance, angle));
            angle += STEP_ANGLE;
        }
        final String path = new EncodedPolyline(latLngList).getEncodedPath();
        this.path += "&path=weight:3%7Ccolor:blue%7Cfillcolor:0x3333ee33%7Cenc:" + path + "&";
        return this;
    }

    public StaticMapURL setZoom(int zoom) {
        this.path += "zoom=" + zoom + "&";
        return this;
    }

    public String build() {
        return this.path + "size=640x640&maptype=satellite&format=jpg&visual_refresh=true";
    }

    public static class MapBuilder {

        private static final String PATH = "https://maps.googleapis.com/maps/api/staticmap?";

        public static StaticMapURL createMap() {
            return new StaticMapURL(PATH);
        }

        //&path=weight:3%7Ccolor:orange%7Cenc:_fisIp~u%7CU}%7Ca@pytA_~b@hhCyhS~hResU%7C%7Cx@oig@rwg@amUfbjA}f[roaAynd@%7CvXxiAt{ZwdUfbjAewYrqGchH~vXkqnAria@c_o@inc@k{g@i`]o%7CF}vXaj\h`]ovs@?yi_@rcAgtO%7Cj_AyaJren@nzQrst@zuYh`]v%7CGbldEuzd@%7C%7Cx@spD%7CtrAzwP%7Cd_@yiB~vXmlWhdPez\_{Km_`@~re@ew^rcAeu_@zhyByjPrst@ttGren@aeNhoFemKrvdAuvVidPwbVr~j@or@f_z@ftHr{ZlwBrvdAmtHrmT{rOt{Zz}E%7Cc%7C@o%7CLpn~AgfRpxqBfoVz_iAocAhrVjr@rh~@jzKhjp@``NrfQpcHrb^k%7CDh_z@nwB%7Ckb@a{R%7Cyh@uyZ%7CllByuZpzw@wbd@rh~@%7C%7CFhqs@teTztrAupHhyY}t]huf@e%7CFria@o}GfezAkdW%7C}[ocMt_Neq@ren@e~Ika@pgE%7Ci%7CAfiQ%7C`l@uoJrvdAgq@fppAsjGhg`@%7ChQpg{Ai_V%7C%7Cx@mkHhyYsdP%7CxeA~gF%7C}[mv`@t_NitSfjp@c}Mhg`@sbChyYq}e@rwg@atFff}@ghN~zKybk@fl}A}cPftcAite@tmT__Lha@u~DrfQi}MhkSqyWivIumCria@ciO_tHifm@fl}A{rc@fbjAqvg@rrqAcjCf%7Ci@mqJtb^s%7C@fbjA{wDfs`BmvEfqs@umWt_Nwn^pen@qiBr`xAcvMr{Zidg@dtjDkbM%7Cd_@
    }

}