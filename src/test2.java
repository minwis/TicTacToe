public class test2 {

    public static void main(String[] args ) {
        int[] arr = new int[] {2,4,8};
        System.out.println(groupSum(0, arr, 10));
    }

    public static boolean groupSum(int i, int[] nums, int target) {
        if(i >= nums.length ) {
            return target == 0;
        }


        if(groupSum(i+1, nums, target - nums[i])) {
            return true;
        }


        if(groupSum(i+1, nums, target)) {
            return true;
        }


        return false;
    }

}
