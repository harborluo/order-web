
// var $ = layui.jquery;

layui.use('laydate', function() {
    var laydate = layui.laydate;

    //常规用法
    laydate.render({
        elem: '#beginDate'
    });

    laydate.render({
        elem: '#endDate'
    });

    //

    laydate.render({
        elem: "#project-form input[name='projectDate']"
    });

    laydate.render({
        elem: "#project-form input[name='beginDate']"
    });

    laydate.render({
        elem: "#project-form input[name='finishDate']"
    });

});

layui.use(['jquery', 'table', 'laypage', 'laydate'], function(){

    var table = layui.table;
    var $ = layui.jquery;

    table.render({
        //其它参数在此省略
        // skin: 'row' //行边框风格
        elem: '#demoTable'
        ,url:'/projects'
        //,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field:'name', width: 200, title: '工程名称', templet:
                    '<div><a href="javascript:void(0)" onclick="editProject({{d.id}})" class="layui-table-link">{{d.name}}</a></div>'},
            {field:'serialNo',  title: '单号'/*, templet:function(d){ return d==''?'未开单':d}*/},
            {field:'projectDate',  title: '工程日期'},
            {field:'cost',  title: '工程总价', align:'right'},
            {
                field: 'costPaid', title: '收款情况', align:'right', templet: function (project) {
                    if (project.cost == project.costPaid) {
                        return "<span style='color: green'>OK</span>"
                    } else {
                        return project.costPaid == null ? '未收款' : project.costPaid;
                    }
                }
            }
            ,{field:'clientName', title: '客户', sort: false}
            ,{field:'clientPhone',  title: '电话', sort: false}
            ,{field:'clientAddress', title: '地址', sort: false}
        ]],
        request: {
            pageName: 'page' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        response: {
            statusName: 'code' //规定数据状态的字段名称，默认：code
            ,statusCode: 200 //规定成功的状态码，默认：0
            ,msgName: 'message' //规定状态信息的字段名称，默认：msg
            ,countName: 'data.total' //规定数据总数的字段名称，默认：count
            ,dataName: 'data.records' //规定数据列表的字段名称，默认：data
        },
        page: true,
        limit : 10,
        limits: [10, 25, 50, 100],
        id: 'demoTable'
    });


    $("button#searchBtn").click(function (ev) {
        console.info("searchBtn clicked.");
        table.reload('demoTable', {
            where: {
                // page: 1,
                serialNo: $("#serialNo").val(),
                isDealDone: $("#isOK").val(),
                isValidate: $('#isValidate').is(':checked') ? "N" : "Y",
                dateType: $("#dateType").val(),
                projectFromDate: $("#dateType").val() == "projectDate" ? $("#beginDate").val() : "",
                projectToDate: $("#dateType").val() == "projectDate" ? $("#endDate").val() : "",
                payFromDate: $("#dateType").val() == "payDate" ? $("#beginDate").val() : "",
                payToDate: $("#dateType").val() == "payDate" ? $("#endDate").val() : ""
            } //设定异步数据接口的额外参数

        });

    });

    $("button#addBtn").click(function (ev) {
        addProject();
    });

    //监听行单击事件（双击事件为：rowDouble）
    table.on('row(demoTable)', function(obj){
        // var data = obj.data;
        //标注选中样式
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    });

});

function addProject() {

    layer.open({
        area: '700px',
        type: 1,
        title: '添加记录：',
        content: layui.jquery("#project-form"),
        btn: ['保存', '取消'],
        btnAlign: 'c',
        yes: function(index, layero){
            //按钮【按钮一】的回调
        },
        保存: function(index, layero){
            //按钮【按钮二】的回调

            //return false 开启该代码可禁止点击该按钮关闭
        },
        取消: function(index, layero){
            //按钮【按钮三】的回调

            //return false 开启该代码可禁止点击该按钮关闭
        }
        ,cancel: function(){
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        },
        success : function () {
            //数据绑定

            layui.jquery("#project-form input[name='name']").val();
            layui.jquery("#project-form input[name='projectDate']").val();
            layui.jquery("#project-form input[name='cost']").val();
            layui.jquery("#project-form input[name='costPaid']").val();
            layui.jquery("#project-form input[name='processCost']").val();
            layui.jquery("#project-form input[name='materialCost']").val();
            layui.jquery("#project-form input[name='beginDate']").val();
            layui.jquery("#project-form input[name='finishDate']").val();
            layui.jquery("#project-form input[name='serialNo']").val();
            layui.jquery("#project-form input[name='clientName']").val();

            layui.jquery("#project-form input[name='clientPhone']").val();
            layui.jquery("#project-form input[name='clientAddress']").val();

            layui.jquery("#project-form input[name='note']").val();

            //项目明细
            layui.table.render({
                elem: '#detailTable'
                , cols: [[ //标题栏
                    {field: 'id', title: 'ID', sort: true}
                    , {field: 'material', title: '材料'}
                    , {field: 'length', title: '长' }
                    , {field: 'width', title: '宽'}
                    , {field: 'quantity', title: '数量'}
                    , {field: 'price', title: '单价'}
                    , {field: 'note', title: '备注'}
                ]]
                , data: []
            });

            //收费明细
            layui.table.render({
                elem: '#feeTable'
                , cols: [[ //标题栏
                    {field: 'id', title: 'ID', sort: true}
                    , {field: 'payNo', title: '收款单号'}
                    , {field: 'length', title: '支付金额' }
                    , {field: 'width', title: '支付日期'}
                    , {field: 'quantity', title: '用途(定金或付款)', width: 140}
                    , {field: 'price', title: '收款人'}
                ]]
                , data: []
            });
            //数据绑定结束
        }

    });
}

function editProject(pid) {
    // layer.alert(JSON.stringify(pid), {
    //     title: '修改记录：'
    // });

    layui.jquery.ajax({url:"/project/"+pid,
        success:function(response){

            layer.open({
                area: '700px',
                type: 1,
                title: '修改记录：',
                content: layui.jquery("#project-form"),
                btn: ['保存', '取消'],
                btnAlign: 'c',
                yes: function(index, layero){
                    //按钮【按钮一】的回调
                },
                保存: function(index, layero){
                    //按钮【按钮二】的回调

                    //return false 开启该代码可禁止点击该按钮关闭
                },
                取消: function(index, layero){
                    //按钮【按钮三】的回调

                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                },
                success : function () {
                    //数据绑定

                    layui.jquery("#project-form input[name='name']").val(response.data.name);
                    layui.jquery("#project-form input[name='projectDate']").val(response.data.projectDate);
                    layui.jquery("#project-form input[name='cost']").val(response.data.cost);
                    layui.jquery("#project-form input[name='costPaid']").val(response.data.costPaid);
                    layui.jquery("#project-form input[name='processCost']").val(response.data.processCost);
                    layui.jquery("#project-form input[name='materialCost']").val(response.data.materialCost);
                    layui.jquery("#project-form input[name='beginDate']").val(response.data.beginDate);
                    layui.jquery("#project-form input[name='finishDate']").val(response.data.finishDate);
                    layui.jquery("#project-form input[name='serialNo']").val(response.data.serialNo);
                    layui.jquery("#project-form input[name='clientName']").val(response.data.clientName);

                    layui.jquery("#project-form input[name='clientPhone']").val(response.data.clientPhone);
                    layui.jquery("#project-form input[name='clientAddress']").val(response.data.clientAddress);

                    layui.jquery("#project-form input[name='note']").val(response.data.note);

                    //项目明细
                    layui.table.render({
                        elem: '#detailTable'
                        , cols: [[ //标题栏
                            {field: 'id', title: 'ID', sort: true}
                            , {field: 'material', title: '材料'}
                            , {field: 'length', title: '长' }
                            , {field: 'width', title: '宽'}
                            , {field: 'quantity', title: '数量'}
                            , {field: 'price', title: '单价'}
                            , {field: 'note', title: '备注'}
                        ]]
                        , data: response.data.details
                    });

                    //收费明细
                    layui.table.render({
                        elem: '#feeTable'
                        , cols: [[ //标题栏
                            {field: 'id', title: 'ID', sort: true}
                            , {field: 'payNo', title: '收款单号', edit: 'text'}
                            , {field: 'length', title: '支付金额', edit: 'text', event: 'number'}
                            , {field: 'width', title: '支付日期', edit: 'text', event: 'date'}
                            , {field: 'quantity', title: '用途(定金或付款)', edit: 'text', width: 140}
                            , {field: 'price', title: '收款人', edit: 'text'}
                        ]]
                        , data: response.data.pays
                    });

                    //数据绑定结束
                    layui.table.on('edit(feeTable)', function(obj){
                        var value = obj.value //得到修改后的值
                            ,data = obj.data //得到所在行所有键值
                            ,field = obj.field; //得到字段
                        layer.msg('[ID: '+ data.id +'] 字段:' + field + ' event:' + obj.event + ' 更改为：'+ value);
                        // if (obj.event==='number' && isNaN(obj.value)) {

                            var selector = obj.tr.find('[data-field=' + obj.field + ']');
                            var oldtext = layui.jquery(selector).text();
                        //     obj.update(oldtext);
                        // }
                    });

                    layui.table.on('tool(feeTable)', function (obj) {
                        var newdata = {};
                        if (obj.event === 'date') {
                            var field = layui.jquery(this).data('field');
                            layui.laydate.render({
                                elem: this.firstChild
                                , show: true //直接显示
                                , closeStop: this
                                , type: 'date'
                                , format: "yyyy-MM-dd"
                                , done: function (value, date) {
                                    newdata[field] = value;
                                    obj.update(newdata);
                                    // alert(JSON.stringify(value))
                                }
                            });
                        }
                    });

                }

            });

        }});
}