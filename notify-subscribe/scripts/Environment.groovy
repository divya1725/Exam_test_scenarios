class Environment {
    public static String[] getDetailsOf(String value) {
        String[] list = new String[6];
        String env = getEnvironmentName(value);
        list[0] = env;
        list[1] = getEOSIP(env);
        list[2] = getEBSIP(env);
        list[3] = getExaData(env);
        list[4] = getExaDataWithHost(env);
        list[5] = getPbd(env);
        list[6] = getCore(env);
        return list;
    }
    public static String getEnvironmentName(String env){
        env = env.toLowerCase();
        if (env.contains("d2")) { return "d2";}
        if (env.contains("d3")) { return "d3";}
        if (env.contains("d4")) { return "d4";}
        if (env.contains("d5")) { return "d5";}
        if (env.contains("d6")) { return "d6";}
        if (env.contains("d7")) { return "d7";}
        if (env.contains("d8")) { return "d8";}
        if (env.contains("d9")) { return "d9";}
        if (env.contains("d10")) { return "d10";}
        if (env.contains("d11")) { return "d11";}
        if (env.contains("d12")) { return "d12";}
        if (env.contains("d13")) { return "d13";}
        if (env.contains("d14")) { return "d14";}
        if (env.contains("d15")) { return "d15";}
        if (env.contains("d16")) { return "d16";}
        if (env.contains("d17")) { return "d17";}
        if (env.contains("n1")) { return "n1";}
        if (env.contains("s1") && env.endsWith("s1")) { return "s1";}
        if (env.contains("s4")) { return "s4";}
        if (env.contains("d1") && env.endsWith("d1")) { return "d1";}
        if (env.contains("s6")) { return "s6";}
        if (env.contains("s8")) { return "s8";}
        if (env.contains("s10")) { return "s10";}
        return null;
    }
    public static String getEOSIP(String env){
        env=getEnvironmentName(env);
        switch(env){
            case "d2": return (getIP_106()+":22411");
            case "d3": return (getIP_107()+":22521");
            case "d4": return (getIP_107()+":23621");
            case "d5": return (getIP_107()+":22561");
            case "d6": return (getIP_108()+":22641");
            case "d7": return (getIP_106()+":20811");
            case "d8": return (getIP_108()+":29221");
            case "d9": return (getIP_212()+":29181");
            case "d10": return (getIP_212()+":29191");
            case "d11": return (getIP_212()+":29201");
            case "d12": return (getIP_212()+":29211");
            case "d13": return (getIP_108()+":29231");
            case "d14": return (getIP_108()+":29241");
            case "d15": return (getIP_108()+":29251");
            case "d16": return (getIP_108()+":29101");
            case "d17": return (getIP_108()+":25781");
            case "s1": return (getIP_245()+":10810");
            case "s4": return (getIP_245()+":15780");
            case "n1": return (getIP_245()+":11190");
            case "d1": return (getIP_r_23()+":21171");
            case "s6": return (getIP_r_245()+":11730");
            case "s8": return (getIP_r_245()+":12390");
            case "s10": return (getIP_r_245()+":11460");
            default: return null;
        }
    }
    public static String getEBSIP(String env){
        env=getEnvironmentName(env);
        switch(env){
            case "d2": return (getIP_79()+":20341");
            case "d3": return (getIP_79()+":20351");
            case "d4": return (getIP_207()+":20361");
            case "d5": return (getIP_79()+":20351");
            case "d6": return (getIP_207()+":20381");
            case "d7": return (getIP_79()+":20351");
            case "d8": return (getIP_79()+":20521");
            case "d9": return (getIP_207()+":20522");
            case "d10": return (getIP_207()+":20523");
            case "d11": return (getIP_207()+":");
            case "d12": return (getIP_207()+":");
            case "d13": return (getIP_207()+":20581");
            case "d14": return (getIP_207()+":20601");
            case "d15": return (getIP_207()+":20611");
            case "d16": return (getIP_207()+":20621");
            case "d17": return (getIP_207()+":20441");
            case "s1": return (getIP_82()+":20411");
            case "s4": return (getIP_82()+":");
            case "n1": return (getIP_40()+":");
            case "d1": return (getIP_r_65()+":20301");
            case "s6": return (getIP_r_65()+":20351");
            case "s8": return (getIP_r_65()+":20271");
            case "s10": return (getIP_r_65()+":");
            default: return null;
        }
    }
    public static String getEam(String env){ 
        if((access(env)).equals("restricted")){ 
            return ("139.114.216.245:10530");
        }
        return ("10.246.89.245:13080");
    }
    public static String getPbd(String env){
        env=getEnvironmentName(env);
        switch(env){
            case "d2":
            case "d5":
            case "d6": return (getPbdIP("d6"));
            case "d3":
            case "d4":
            case "d7":
            case "s1": return (getPbdIP("s2"));
            case "d8":
            case "d9":
            case "d10":
            case "d11":
            case "d12":
            case "d13":
            case "d14":
            case "d15":
            case "d16": return (getPbdIP("s5"));
            case "d17":
            case "s4": return (getPbdIP("s4"));
            case "n1": return (getPbdIP("n1"));
            case "d1":
            case "s6":
            case "s8":
            case "s10": return (getPbdIP("s6"));
            default: return null;
        }
    }
    public static String getCore(String env){
        env=getEnvironmentName(env);
        switch(env){
            case "d2":
            case "d3":
            case "d4":
            case "d6":
            case "d7":
            case "d8":
            case "d9":
            case "d10":
            case "d11":
            case "d12":
            case "d13":
            case "d14":
            case "d15":
            case "d16":
            case "s1":
            case "n1":
            case "d17":
            case "s4": return (getCoreIP("v"));
            case "d5": return (getCoreIP("s2"));
            case "d1":
            case "s6":
            case "s8":
            case "s10": return (getCoreIP("u"));
            default: return null; 
        }
    }
    
    public static String getSidValue(String env){
        env=getEnvironmentName(env);
        switch(env){
            case "d2": return ("pwh_g_d2");
            case "d3": return ("pwh_g_d3");
            case "d4": return ("pwh_g_d4");
            case "d5": return ("pwh_g_d5");
            case "d6": return ("pwh_g_d6");
            case "d7": return ("pwh_g_d7");
            case "d8": return ("pwh_g_d8");
            case "d9": return ("pwh_g_d9_exa_12c");
            case "d10": return ("pwh_g_d10");
            case "d11": return ("pwh_g_d11");
            case "d12": return ("pwh_g_d12_pdb");
            case "d13": return ("pwh_g_d13");
            case "d14": return ("pwh_g_d14");
            case "d15": return ("pwh_g_d15");
            case "d16": return ("pwh_g_d16");
            case "d17": return ("pwh_g_d17");
            case "s1": return ("pwh_g_s1_19c");
            case "s4": return ("pwh_g_s4");
            case "n1": return ("pwh_g_n1");
            case "d1": return ("pwh_r_devel");
            case "s6": return ("pwh_r_s6");
            case "s8": return ("pwh_r_s8_19c");
            case "s10": return ("pwh_r_s10");
            default: return null;
        }
    }
    public static String getPbdIP(String pbd){
        switch (pbd) {
            case "s2" : return (getIP_245()+":10780");
            case "s4" : return (getIP_245()+":10870");
            case "s5" : return (getIP_245()+":10880");
            case "s6" : return (getIP_r_245()+":10850");
            case "a4" : return (getIP_r_245()+":12340");
            case "n1" : return (getIP_245()+":11230");
            case "d6" : return (getIP_108()+":20891");
            default   : return null;
        }
    }
    public static String getCoreIP(String core){
        switch (core) {
            case "s2": return (getIP_245()+":10700");
            case "u" : return (getIP_r_245()+":10730");
            case "c" : return (getIP_r_245()+":13020");
            case "a" : return (getIP_r_245()+":10120");
            case "v" : return (getIP_245()+":10710");
            default  : return null;
        }
    }
    public static String getFtsEOS(String env){
        if (access(env).equals("restricted")){
            return ("139.114.222.26");
        }
        return ("10.246.89.245");
    }
    public static String getFtsEBS(String env){
        if (access(env).equals("restricted")){
            return ("100.200.300.400");
        }
        return ("10.150.187.12"); 
    }
    public static String getIP_40()     { return ("10.150.187.40");}
    public static String getIP_79()     { return ("10.246.89.79");}
    public static String getIP_82()     { return ("10.246.89.82");}
    public static String getIP_106()    { return ("10.246.89.106");}
    public static String getIP_107()    { return ("10.246.89.107");}
    public static String getIP_108()    { return ("10.246.89.108");}
    public static String getIP_207()    { return ("10.246.89.207");}
    public static String getIP_212()    { return ("10.246.89.212");}
    public static String getIP_245()    { return ("10.246.89.245");}
    public static String getIP_r_13()   { return ("139.114.216.13");}
    public static String getIP_r_23()   { return ("139.114.216.23");}
    public static String getIP_r_65()   { return ("139.114.216.65");}
    public static String getIP_r_141()  { return ("139.114.216.141");}
    public static String getIP_r_245()  { return ("139.114.216.245");}

    public static String getExaData(String env){ return ("jdbc:oracle:thin:@"+getConnectionString(env)); }
    // public static String getExaDataWithHost (String env){ return ("jdbc:oracle:thin:pwhdata/pwh@//"+getConnectionString(env)); }
    public static String getExaDataWithHost (String env){ return ("jdbc:oracle:thin:pwhdata/pwh@"+getConnectionString(env)); }
    public static String getConnectionString(String env){
        String connection = getSidValue(env);
        def host="dlt-exa853-scan.unix.cosng.net";
        switch(connection) {
            case "pwh_g_d2":
            case "pwh_g_d3":
            case "pwh_g_d4":
            case "pwh_g_d6":
            case "pwh_g_d7":
            case "pwh_g_d9_exa_12c":
            case "pwh_g_d10":
            case "pwh_g_d11":
            case "pwh_g_s1_19c":
            case "pwh_g_n1":
            case "pwh_g_d12_pdb": return ("(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST="+host+")(PORT=1530)))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME="+connection+")))");
            case "pwh_r_s6":
            case "pwh_r_a1":
            case "pwh_r_a4":
                host = "dlt-exa851-scan.unix.cosng.net";
                return ("(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST="+host+")(PORT=1530)))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME="+connection+")))");
            case "pwh_g_d8": return ("(DESCRIPTION=(CONNECT_TIMEOUT=10)(RETRY_COUNT=3)(RETRY_DELAY=1)(TRANSPORT_CONNECT_TIMEOUT=1)(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST="+host+")(PORT=1530)))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME="+connection+")))");
            case "pwh_r_s8_19c": host = "dlt-exa851-scan.unix.cosng.net";
                return ("(DESCRIPTION=(CONNECT_TIMEOUT=10)(RETRY_COUNT=3)(RETRY_DELAY=1)(TRANSPORT_CONNECT_TIMEOUT=1)(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST="+host+")(PORT=1530)))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME="+connection+")))");
            case "pwh_g_d13":
            case "pwh_g_d14":
            case "pwh_g_d15":
            case "pwh_g_d16": return ("(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.246.89.97)(PORT=1530))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME="+connection+")))");
            case "pwh_g_d5":
            case "pwh_g_d17":
            case "pwh_g_s4": return ("10.246.89.97:1530/"+connection);
            case "pwh_r_devel": return ("139.114.222.25:1530/"+connection);
            default: return null;
        }
    }
    public static String access(String env){
        env=getEnvironmentName(env).toLowerCase();
        switch(env){
            case "d1":
            case "s6":
            case "s8":
            case "s10": return ("restricted");
            default: return ("global");
        }
    }
}