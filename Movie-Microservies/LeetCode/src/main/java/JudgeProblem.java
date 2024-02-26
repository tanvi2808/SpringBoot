import java.util.*;
import java.util.stream.Collectors;

public class JudgeProblem {

    public static void main(String[] args) {
        JudgeProblem judgeProblem = new JudgeProblem();
        int[][] trusts = {{8,5},{5,9},{1,6},{4,9},{3,4},{2,3},{7,6},
                {1,2},{1,7},{2,6},{2,7},{3,6},{4,8},
                {4,6},{5,1},{5,6},{7,1},{8,9},{8,6},{9,6}
        };
       // int[][] trusts = {{1,3},{1,4},{2,3}};
      //  int[][] trusts = {};

        System.out.println("Judge is "+  judgeProblem.findJudge(9, trusts));
    }


        public int findJudge(int n, int[][] trust) {
            HashMap<Integer, List<Integer>> hashMap = new HashMap();

            if(trust.length==0)
                if(n==1)
                    return 1;
                else return -1;
             for(int i=0; i<(trust).length; i++){
                 if(hashMap.containsKey(trust[i][0])){
                     System.out.println("contains " + trust[i][0]);
                     List<Integer> list = hashMap.get(trust[i][0]);
                     list.add(trust[i][1]);
                     hashMap.replace(trust[i][0], list);
                }
                 else {
                     System.out.println("does not contains " + trust[i][0]);
                     List<Integer> trustedBy = new ArrayList<>();
                     trustedBy.add(trust[i][1]);
                     hashMap.put(trust[i][0], trustedBy );
                 }
             }

            int judgeNum = -1;
            int missingnumbers = 0;
             Set hashMapPersons = hashMap.keySet();
            List<Integer> collect = (List<Integer>) hashMapPersons.stream().sorted().collect(Collectors.toList());
            System.out.println(collect.size());
            if(collect.size()!=(n-1) )
                return -1;

            for(int i=0;i<=n-1;i++)
                if (!collect.contains(i+1))
                    judgeNum = i + 1;

            System.out.println("possible judge is " + judgeNum);
//             int anyRecordNo = hashMap.keySet().iterator().next();
//
//             List<Integer> trustsoffirst  = hashMap.get(anyRecordNo);
//             Iterator<Integer> itr = trustsoffirst.listIterator();
//
//
//             // get possible judge
//
//             while(itr.hasNext() && judgeNum==-1) {
//                 int num = itr.next();
//                 if (!hashMap.containsKey(num))
//                     judgeNum = num;
//
//             }
//             if (judgeNum==-1)
//                    return -1;

       // make sure possible judge is trusted by all
            for (Map.Entry<Integer, List<Integer>> entry : hashMap.entrySet())
                  {
                     if(!entry.getValue().contains(judgeNum))
                         return -1;
            }

            return judgeNum;

   }
}
