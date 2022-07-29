class Bank {
    public static String[] getDetailsOf(String org_id) {           
        String[] list = new String[5];
        String orgid = getOrgId(org_id);
        list[0] = orgid;
        list[1] = getOldOrgId(orgid);
        list[2] = getCredentials(orgid);
        list[3] = getBic(orgid);
        list[4] = getBankName(orgid);
        return list;
    }
    public static String getOrgId(String orgid){
        switch(orgid) {
            case "1801":
            case "1802": return ("1802");
            case "2489":
            case "2544": return ("2544");
            case "3201":
            case "3229": return ("3201");
            case "3240":
            case "3249": return ("3240");
            case "3400":
            case "3411": return ("3411");
            case "3601":
            case "3625": return ("3625");
            case "3890":
            case "3702": return ("3702");
            case "4201":
            case "4210": return ("4201");
            case "4702":
            case "4701": return ("4701");
            case "9001":
            case "9898": return ("9001");
            case "9021":
            case "9038": return ("9021");
            case "9055":
            case "9057": return ("9055");
            case "35803100": return ("35803100");
            default: null;
        }
    }
    public static String getOldOrgId(String orgid){
        switch(orgid) {
            case "1801":
            case "1802": return ("1801");
            case "2489":
            case "2544": return ("2489");
            case "3201":
            case "3229": return ("3229");
            case "3240":
            case "3249": return ("3249");
            case "3400":
            case "3411": return ("3400");
            case "3601":
            case "3625": return ("3601");
            case "3890":
            case "3702": return ("3890");
            case "4201":
            case "4210": return ("4210");
            case "4702":
            case "4701": return ("4702");
            case "9001":
            case "9898": return ("9898");
            case "9021":
            case "9038": return ("9038");
            case "9055":
            case "9057": return ("9057");
            case "35803100": return ("35803100");
            default: null;
        }
    }
    public static String getBic(String orgid){
        switch(orgid) {
            case "1801":
            case "1802": return ("SHEDNO22XXX");
            case "2489":
            case "2544": return ("VEFONO21XXX");
            case "3201":
            case "3229": return ("SPRONO22XXX");
            case "3240":
            case "3249": return ("HAUGNO21XXX");
            case "3400":
            case "3411": return ("FANANOB1XXX");
            case "3601":
            case "3625": return ("SPAVNOBBXXX");
            case "3890":
            case "3702": return ("FANANOB1XXX");
            case "4201":
            case "4210": return ("SPTRNO22XXX");
            case "4702":
            case "4701": return ("SNOWNO22XXX");
            case "9001":
            case "9898": return ("LABANOKKXXX");
            case "9021":
            case "9038": return ("BNPANOK0XXX");
            case "9055":
            case "9057": return ("HANDNOKKXXX");
            case "35803100": return ("HANDFIKKXXX");
            default: null;
        }
    }
    public static String getCredentials(String orgid){
        switch(orgid) {
            case "1801":
            case "1802": return ("031110130104004259CjG5fYQwiCKYYovMhb%2FrJmTJZQe%2BFzz%2FRASphHZEo9Vo%2Bzq0WRhPZ%2FP3hY9BTDejrSIpQnXuqKD%2FMjoNuGS8RMPTmTCgkOmjGPUUQDIt8vpbiJn4Fc6bD%2F0%2B%2FgX%2BkhndHfX94rnv%2BvYLqKvAWlCL3GmfsOE%2BimPYAkyRKA%2FtIa1gSbV8PL7TnEYVTVAJx4r68CQtGWcP1JJdSy2emkS35z5zlVuQx1%2FZuR5EQEMBt0%3DMJ9mr8MlUz4bcCd6zAOV6sEczhY54JHkm33YPoXqjRlyrgjeDwGsKf%2Fcq92g4AzymsGcvdgyw4xZMm8dKHdTXtabRriIOvYGVB4iPLTunaQnnRzAImVO%2FbA5Fyss6UFyxc8WoWZlYQEjBGmq16rPU6ASBtaf1OhC18rl%2BAEiruY%3D");
            case "2489":
            case "2544": return ("031110130104004250tu4YEEzPmBtrqnqspzaepmTJZQe%2BFzz%2FRASphHZEo9Vo%2Bzq0WRhPZ%2FP3hY9BTDejrSIpQnXuqKD%2FMjoNuGS8TkX6gR1agFyi03KQsWFB8BXfj%2BAtAgQbSaQcGy0csN5dHfX94rnv%2BvYLqKvAWlCL3GmfsOE%2BimPYAkyRKA%2FtIa1gSbV8PL7TnEYVTVAJx4r68CQtGWcP1JJdSy2emkS3y6yjge28rRaLF%2FZDAw52VE%3DF%2BBuvl5Swo7QVycQAlssDjzA%2FqUYwG5I%2Bn9Yc77Cs5IifWIRMpjIt89tk4zeyJMN6oeBYS9lDE7io0LHd6JJOdmFGZWyNUfk5%2BNO8WAxUjXVP4S5ZgUBHouwzBpDTyarrLwle0lsgGHBvaIeSsJ5ysnvZLV4aJjU2BawhuaDYjs%3D");
            case "3201":
            case "3229": return ("03111013010400425%2BTtmFtxR%2FMqdvgamK6k6d5mTJZQe%2BFzz%2FRASphHZEo9Vo%2Bzq0WRhPZ%2FP3hY9BTDejrSIpQnXuqKD%2FMjoNuGS8RMPTmTCgkOmjGPUUQDIt8sfdn%2FX0ESHB%2BC%2Fdu2%2FMjDAdHfX94rnv%2BvYLqKvAWlCL3GmfsOE%2BimPYAkyRKA%2FtIa1gSbV8PL7TnEYVTVAJx4r68CQtGWcP1JJdSy2emkS3yVm%2FZktkwGVxbzUt0cgEVo%3DYOylu9Q2hknEBdhKBQZ3y0EhkOuxmxzg00bLf1JlN43718zhpPBrie07bfHhiXcpnwAOucAuDAVGmJ3JPt3B%2Fy4eGDxVLddD%2FplarxetvAhn7R38koB30sqnT0sTXTOf7%2FhYaUoHRhGQtKHfJ%2FsYf82RKwGF2Q96e6ej0lZQ%2BlQ%3D");
            case "3240":
            case "3249": return ("03111013010400425f1NrHPQZaxr54KU0fLjn7JmTJZQe%2BFzz%2FRASphHZEo9Vo%2Bzq0WRhPZ%2FP3hY9BTDejrSIpQnXuqKD%2FMjoNuGS8RMPTmTCgkOmjGPUUQDIt8v5TVrl9i9Ebz5HgsZTR9midHfX94rnv%2BvYLqKvAWlCL3GmfsOE%2BimPYAkyRKA%2FtIa1gSbV8PL7TnEYVTVAJx4r68CQtGWcP1JJdSy2emkS30WgrppswDaomRTH1dsyqkc%3DPH8qz%2F7z%2FaW8t%2B7QUJjmEFuYSy9u1EJm6%2Fm98IN2CQxqfaenI2SpMloheua0L6go5YGKz5IwQjs%2F42NK0N5Mwka10DNUjhhdD4qYodUsk8FMEgJyW54n2YfD1mN9Kngx1ybMzyRzEdwXux7OAa6aKpdblmcwALeV1%2F380WYNBiI%3D");
            case "3400":
            case "3411": return ("03111013010400425PXR71G2bmEWm1n7P5D5kjpmTJZQe%2BFzz%2FRASphHZEo9Vo%2Bzq0WRhPZ%2FP3hY9BTDejrSIpQnXuqKD%2FMjoNuGS8RMPTmTCgkOmjGPUUQDIt8uFXnVMu4D8yollgi6lmcPmdHfX94rnv%2BvYLqKvAWlCL3GmfsOE%2BimPYAkyRKA%2FtIa1gSbV8PL7TnEYVTVAJx4r68CQtGWcP1JJdSy2emkS33tKhg3hIT5%2BMB9%2Fz7JtkwE%3DHMPxPioE2MGnA009bnVmZ4FixALmwieXnhUIrNK9vNDWL4yldrK%2Bpz9D3y4D%2FDISsfJfzlhjs58ySaaQ6Rmg0Ylo93glQ0rXKEmy%2FsonTDzICahURTNiFU%2Fkv7QBaN7gUxy301GWLze19oyRyymJXHB1OWkGdwyFHtzVWlgRCzI%3D");
            case "3601":
            case "3625": return ("03110013010400445gft3cRnCLPWWq8xlQ7RTFGToxV2ar6TuwU6Z3UeBuPGOtIilCde6ooP8yOg24ZLxjrSIpQnXuqKD/MjoNuGS8Rw4mB1dHsCbdEPzxdSUScEOatyHX5qqpMbWdg9sXd9OjrSIpQnXuqKD/MjoNuGS8Y60iKUJ17qig/zI6DbhkvGOtIilCde6ooP8yOg24ZLxjrSIpQnXuqKD/MjoNuGS8VQ+cJDNKa9VYZxw7KIYbHB8cLeYBVPv9zeRx72/R0ziZwLpDJ3GXhXq1GEwqQfSKYJ2tYpapZTAlGka5wOfU82PaZrpEiGpF2MtWFUNZT5mGde8AMKM7YjZLq7bNqLJkX2jWnq8NpJv3mKcnhxevntuGj+LNVvt9Yd3A4XTDgHK8R22vZAGljeNQwWelwnd9cTDrDHgPSubME8PWzJvEoc=");
            case "3890":
            case "3702": return ("03111013010400425PXR71G2bmEWm1n7P5D5kjpmTJZQe%2BFzz%2FRASphHZEo9Vo%2Bzq0WRhPZ%2FP3hY9BTDejrSIpQnXuqKD%2FMjoNuGS8RMPTmTCgkOmjGPUUQDIt8uFXnVMu4D8yollgi6lmcPmdHfX94rnv%2BvYLqKvAWlCL3GmfsOE%2BimPYAkyRKA%2FtIa1gSbV8PL7TnEYVTVAJx4r68CQtGWcP1JJdSy2emkS33tKhg3hIT5%2BMB9%2Fz7JtkwE%3DHMPxPioE2MGnA009bnVmZ4FixALmwieXnhUIrNK9vNDWL4yldrK%2Bpz9D3y4D%2FDISsfJfzlhjs58ySaaQ6Rmg0Ylo93glQ0rXKEmy%2FsonTDzICahURTNiFU%2Fkv7QBaN7gUxy301GWLze19oyRyymJXHB1OWkGdwyFHtzVWlgRCzI%3D");
            case "4201":
            case "4210": return ("03110012010400425kglPboRi3h16OVtVL1neI9CfuAtjCBO603eS0IgWm1WWIO7EfpSL4DE%2FlDFi69n1sSaabKNt2t%2BxJppso23a37EmmmyjbdrfutbV53%2FUXGOxjDkLmirISLEmmmyjbdrfsSaabKNt2t%2FXzlyjxYnxhcG46wcbIKp%2FsSaabKNt2t9wBUG0SyIoqFfig80Gq4uXVBtimDzTiu2xJppso23a37Emmmyjbdrf8wP73CNrvpw%3DH5LS2JD%2BVebr3PytlKAocCy8%2FCcrl7tRSepyb0jhKW8A8k07nuvorl7Xoscy%2BybGjXIr2Zj%2Fvprt%2BD1IueT5rqEcvxyyr%2FYELRuLkDS94SfjVw%2FszcGGYfeWnI2Z1NwXorQAZPvT5U6Edafp2sbvWiNKkrzCmFJ4YcuHbGSJCYk%3D");
            case "4702":
            case "4701": return ("03111013010400425PXR71G2bmEWm1n7P5D5kjpmTJZQe%2BFzz%2FRASphHZEo9Vo%2Bzq0WRhPZ%2FP3hY9BTDejrSIpQnXuqKD%2FMjoNuGS8RMPTmTCgkOmjGPUUQDIt8uFXnVMu4D8yollgi6lmcPmdHfX94rnv%2BvYLqKvAWlCL3GmfsOE%2BimPYAkyRKA%2FtIa1gSbV8PL7TnEYVTVAJx4r68CQtGWcP1JJdSy2emkS33tKhg3hIT5%2BMB9%2Fz7JtkwE%3DHMPxPioE2MGnA009bnVmZ4FixALmwieXnhUIrNK9vNDWL4yldrK%2Bpz9D3y4D%2FDISsfJfzlhjs58ySaaQ6Rmg0Ylo93glQ0rXKEmy%2FsonTDzICahURTNiFU%2Fkv7QBaN7gUxy301GWLze19oyRyymJXHB1OWkGdwyFHtzVWlgRCzI%3D");
            case "9001":
            case "9898": return ("03111013010400425eblN3HH6rvLDJvGWdXyMcJmTJZQe%2BFzz%2FRASphHZEo9Vo%2Bzq0WRhPZ%2FP3hY9BTDejrSIpQnXuqKD%2FMjoNuGS8RMPTmTCgkOmjGPUUQDIt8vrXrjxcPc4VoMCtTDQmUiYdHfX94rnv%2BvYLqKvAWlCL3GmfsOE%2BimPYAkyRKA%2FtIa1gSbV8PL7TnEYVTVAJx4r68CQtGWcP1JJdSy2emkS378HnJBf3QS1prLJ4z1O9g8%3DTFEUjfUUes7i5g0M1GyYPjDw66rjprIPQ%2Fab8h0TQJCQL8iPHO%2F%2FwKiwXKIY7W0ZfCst7qIFqtm63Ak%2Ff%2FNlWv0g8HWyfauLo%2FXPclMaLiE5VKhKWv0stqvLtH%2FFHI6%2BAkz%2FcI3yACJX7itX3WeBIHPGrTXBlm9qwe21PbKfTbs%3D");
            case "9021":
            case "9038": return ("");
            case "9055":
            case "9057": return ("03111013010400425D%2F7H7Z5gg09DwvXpTjq9yZmTJZQe%2BFzz%2FRASphHZEo9Vo%2Bzq0WRhPZ%2FP3hY9BTDejrSIpQnXuqKD%2FMjoNuGS8SPw4jIv3rXSzgpq4N9jjv%2FiyOYGOgvTSRjrfX3AqOt%2FdHfX94rnv%2BvYLqKvAWlCL3GmfsOE%2BimPYAkyRKA%2FtIa1gSbV8PL7TnEYVTVAJx4r68CQtGWcP1JJdSy2emkS301roLRkJpcTJ5euYXWp28M%3DDT0PUyyJYPtJYATi9lKmcZlZ%2FQ4AfLGdMV4vQsJLHsWdw0nazr2OjOS6N%2B7pP1Q2e%2BwGoMDIhW2TQtY68ZAqc8hAK%2FCY0hxv9PPFSi0NaV3y%2FvTZGib7vNufOAZaaAUJa4286UAGn2RDUYHbhBIGRCpWgkXKN4jp0X%2FWvSeRkvA%3D");
            case "35803100": return ("03111013010400425GWfpot6k%2Fmks%2FEYCsehqE%2B6ypxDnhoQe1cPSToeH6a9Vo%2Bzq0WRhPZ%2FP3hY9BTDejrSIpQnXuqKD%2FMjoNuGS8RMPTmTCgkOmjGPUUQDIt8sl98Nba9Trkx34QzE3Ly%2BmdHfX94rnv%2BvYLqKvAWlCL3GmfsOE%2BimPYAkyRKA%2FtIa1gSbV8PL7TnEYVTVAJx4r68CQtGWcP1JJdSy2emkS34HmOXBCM1LvQxeD1DqTYeQ%3Dup6z7NbZXlvojNz0S2ximCbT2zXJUjXrDKqtkwb0CoA7SX%2BL7Ecls7IhwUH4DcYjClsKGiLdDbYLBGJ2oLAbswvr8PWxvigfnMuEwm9YJZ0eahd%2B4V6Qo1W%2FRcAT1Vrjqdz5yx1c7Zmna3px9f7QmVSpv0Ki%2FjxI%2FdczJpdrLlA%3D");
            default: null;
        }
    }
    public static String getBankName(String orgid){
        switch(orgid) {
            case "1801":
            case "1802": return ("Sparebank 1 Ostlandet");
            case "2489":
            case "2544": return ("SpareBank 1 BV");
            case "3201":
            case "3229": return ("Sparebank 1 SR-Bank");
            case "3240":
            case "3249": return ("Haugesund Sparebank");
            case "3400":
            case "3411": return ("Fana Sparebank");
            case "3601":
            case "3625": return ("Sparebanken Vest");
            case "3890":
            case "3702": return ("Fana Sparebank");
            case "4201":
            case "4210": return ("SpareBank 1 SMN");
            case "4702":
            case "4701": return ("Sparebank 1 Nord-Norge");
            case "9001":
            case "9898": return ("Bank 1 Oslo AS");
            case "9021":
            case "9038": return ("BNP Paribas");
            case "9055":
            case "9057": return ("Handelsbanken Norway");
            case "35803100": return ("Handelsbanken Finland");
            default: null;
        }
    }
}