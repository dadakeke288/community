$( function() {
    $("#ques-comment-submit").click(function () {
        var questId = $("#question-id").val();
        var content = $("#comment-text").val();
        $.ajax({
           type:"POST",
           url:"/comment",
            contentType:"application/json",
           data:JSON.stringify({
               "parentId":questId,
                "content":content,
                "type":1
            }),
           success:function succeed() {
                console.log(responce);
           },
            dataType:"json"
        });
    });
});