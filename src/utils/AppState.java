package utils;

import ui.Dashboard;

public class AppState {

    private static Dashboard dashboard;

    public static void setDashboard(Dashboard d){

        dashboard = d;

    }

    public static Dashboard getDashboard(){

        return dashboard;

    }

}