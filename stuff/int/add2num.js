
 // Definition for singly-linked list.
  function ListNode(val, next) {
      this.val = (val===undefined ? 0 : val)
      this.next = (next===undefined ? null : next)
  }
 
/**
 * @param {ListNode} l1
 * @param {ListNode} l2
 * @return {ListNode}
 */
var addTwoNumbers = function(l1, l2) {
    var first =0;
    var second = 0;
    var tenth = 1;
    var res = 0;
    while(l1 || l2){
        if(l1){
            first += l1.val * tenth;    
            console.log(l1.val, first);
            l1 = l1.next;
        }
        if(l2){
            second += l2.val * tenth;
            console.log(l2.val, second);
            l2 = l2.next;
        }
        tenth = tenth * 10;
    }
    res = first+second;
    var output = new ListNode();
    output.val =res%10;
    var last = output;
    output.next = null;
    console.log("out ",output);
    console.log("next ",next);
    res= res/10;
    console.log("div ",res);
    res = Math.floor(res);
    console.log("floor ",res);
    while(res){
        var next = new ListNode();
        last.next = next;
        next.val = res%10;
        last = next;
        console.log("next ",next);
        console.log("last ",last);
        res= res/10;
        console.log("div ",res);
        res = Math.floor(res);
        console.log("floor ",res);
    }
    console.log(first, second, output);
    return output;
};