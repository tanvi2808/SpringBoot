import java.util.*;

public class CheapestFlightWithKStops {


    static HashMap<Integer,HashMap<Integer,Integer>> citiesRoutes ;



    private  static int findFlight(int src, int dest, int noOfStops, int initPrice, HashMap<Integer,Integer> routesFromThisCity){
     //   HashMap<Integer, Integer> routesFromThisCity = new HashMap<>();
          int minPrice = -1;
        if(routesFromThisCity == null)
            return -1;

        if (noOfStops == 0){
            if(routesFromThisCity.containsKey(dest))
                return routesFromThisCity.get(dest);
            else return -1; // check its -1 or 0
        }


        Iterator<Integer> itr =routesFromThisCity.keySet().iterator();

        while(itr.hasNext()) {
            int finalPrice=0;
            int nextCity = itr.next();
          //  initPrice = initPrice + routesFromThisCity.get(src);
            if(dest==nextCity)
                finalPrice = initPrice + routesFromThisCity.get(nextCity);
            else {
              int priceFromHere =    findFlight(nextCity, dest, noOfStops - 1, initPrice, citiesRoutes.get(nextCity));
              if(priceFromHere != -1)
                    finalPrice = routesFromThisCity.get(nextCity) + priceFromHere;
            }
            if(minPrice<=0 || finalPrice < minPrice)
                minPrice = finalPrice;
        }
        return minPrice;
    }

    public static void main(String[] args) {
    CheapestFlightWithKStops sol = new CheapestFlightWithKStops();

     //   System.out.println(sol.findCheapestPrice(5, new int[][]{{4,1,1},{1,2,3},{0,3,2},{0,4,10},{3,1,1},{1,4,3}},2,1,1));
       // System.out.println(sol.findCheapestPrice(3, new int[][]{{0,1,100},{1,2,100},{0,2,500}},0,2,1));
     //System.out.println(sol.findCheapestPrice(1, new int[][]{},0,0,0));
        System.out.println(sol.findCheapestPrice(10, new int[][]{{3,4,4},{2,5,6},{4,7,10},{9,6,5},{7,4,4}, {6,2,10},{6,8,6},{7,9,4},{1,5,4},{1,0,4},{9,7,3},{7,0,5},{6,5,8},{1,7,6},{4,0,9},{5,9,1},{8,7,3},{1,2,6},{4,1,5},{5,2,4},{1,9,1},{7,8,10},{0,4,2},{7,2,8}},6,0,7));
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

        citiesRoutes = new HashMap();
        for(int i=0; i< flights.length;i++){

            if(citiesRoutes.containsKey(flights[i][0])) {
                HashMap<Integer,Integer> hashMap = citiesRoutes.get(flights[i][0]);
                hashMap.put(flights[i][1], flights[i][2]);
            }

                else{
                    HashMap<Integer,Integer> hashMap = new HashMap<Integer,Integer>();
                hashMap.put(flights[i][1], flights[i][2]);
                citiesRoutes.put(flights[i][0], hashMap);
            }

        }

        return findFlight(src,dst,k,0, citiesRoutes.get(src));

    }
}
