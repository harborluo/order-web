
function isNumber(val) {
    var regPos = /^\d+(\.\d+)?$/; //非负浮点数
    var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    if (regPos.test(val) || regNeg.test(val)) {
        return true;
    } else {
        return false;
    }
}

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
        ,defaultToolbar: ['filter', 'print', 'exports', {
            title: '提示' //标题
            ,layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            ,icon: 'layui-icon-tips' //图标类名
        }]
        ,toolbar: '#toolbarProject'
        //,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        // ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        ,cols: [[
            {type: 'checkbox', fixed: 'left', totalRowText: '合计'},
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
        totalRow: true, //开启合计行
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
        id: 'demoTable',
        done : function (res, curr) {
            // layer.alert("数据加载完成...", {icon: 1});
            var costTotal = res.map.costTotal;
            var costPaidTotal = res.map.costPaidTotal;
            var unPaidTotal = costTotal - costPaidTotal;
            this.elem.next().find('.layui-table-total td[data-field="cost"] .layui-table-cell').text(costTotal);
            this.elem.next().find('.layui-table-total td[data-field="costPaid"] .layui-table-cell').text(costPaidTotal);
            this.elem.next().find('.layui-table-total td[data-field="clientName"] .layui-table-cell').text('余款未收: ' + unPaidTotal);
        }
    });

    //监听行单击事件（双击事件为：rowDouble）
    table.on('row(demoTable)', function(obj){
        // var data = obj.data;
        //标注选中样式
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    });

    //监听工具栏按钮点击
    table.on('toolbar(demoTable)', function(obj){
        if(obj.event === 'search'){
            searchProject();
        } else if(obj.event === 'add'){
            addProject();
        } else if(obj.event === 'del'){
            delProject();
        } else if(obj.event === 'exp'){
            console.log('excel export');
        }

    });

});

function searchProject() {
    console.info("searchBtn clicked.");
    var $ = layui.jquery;
    layui.table.reload('demoTable', {
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
}

function delProject() {
    var selectedData = layui.table.checkStatus('demoTable').data;
    // layer.alert(JSON.stringify(selectedData), {icon: 1});
    var ids = [];
    for (let p of selectedData) {
        ids.push(p.id);
    }

    if (ids.length === 0) {
        layer.alert("没选中数据.", {icon: 1});
        return;
    }

    layer.confirm('确定删除？', {icon: 3, title:'提示'}, function(index){
        //do something
        layui.jquery.ajax({
            type: 'delete',
            url: "/project",
            data: JSON.stringify(ids),
            dataType: "json",
            contentType : 'application/json;charset=UTF-8',
            success: function (res){
                // layui.jquery("button#searchBtn").click();
                searchProject();
            }
        });
        layer.close(index);
    });

    console.log("remove projects with ids " + JSON.stringify(ids));


}

function addProject() {

    layer.open({
        area: '700px',
        type: 1,
        title: '添加记录：',
        content: layui.jquery("#project-form"),
        btn: ['保存', '取消'],
        btnAlign: 'c',
        yes: function(index, layero){
            //按钮 [保存] 的回调
            var newProject = layui.form.val("project-form");

            newProject['details'] = layui.table.cache["detailTable"]

            // layer.alert(JSON.stringify(data1), {icon: 1});

            layui.jquery.ajax({
                type: 'post',
                url: "/project",
                data: JSON.stringify(newProject),
                dataType: "json",
                contentType : 'application/json;charset=UTF-8',
                success: function (res){
                    // layui.jquery("button#searchBtn").click();
                    searchProject();
                    layer.closeAll();
                },
                error: function (res) {
                    layer.alert(JSON.stringify(res.responseJSON.message), {icon: 2});
                }

            });
        },
        btn2: function(index, layero){
            //按钮 '取消' 的回调

            //return false 开启该代码可禁止点击该按钮关闭
        }
        ,cancel: function(){
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        },
        success : function () {
            //数据绑定

            layui.form.val("project-form", {
                "id" : ""
                ,"name": ""
                ,"projectDate": ""
                ,"cost": ""
                ,"costPaid": ""
                ,"processCost": ""
                ,"materialCost": ""
                ,"beginDate": ""
                ,"finishDate": ""
                ,"serialNo": ""
                ,"clientPhone": ""
                ,"clientAddress": ""
                ,"note": ""
            });

            //项目明细
            layui.table.render({
                elem: '#detailTable'
                ,defaultToolbar: [] //这里在右边显示
                ,toolbar: '#toolbarDetail'
                , cols: [[ //标题栏
                    {field: 'id', title: 'ID', sort: true}
                    , {field: 'material', title: '材料'}
                    , {field: 'length', title: '长', align: 'right' }
                    , {field: 'width', title: '宽', align: 'right'}
                    , {field: 'quantity', title: '数量', align: 'right'}
                    , {field: 'price', title: '单价', align: 'right'}
                    , {field: 'note', title: '备注'}
                ]]
                , data: []

            });

            // layui.layer.alert(JSON.stringify("toolbar(toolbarDetail)"), {icon: 1});

            layui.table.on('toolbar(detailTable)', function(obj){

                if(obj.event === 'add'){
                    layui.layer.open({
                        // area: '700px',
                        type: 1,
                        title: '添加 - 项目明细：',
                        content: layui.jquery("#project-detail-form"),
                        btn: ['保存', '取消'],
                        btnAlign: 'c',
                        yes: function(index, layero) {
                            //按钮 [保存] 的回调
                            var newRow = layui.form.val("project-detail-form");

                            // layer.alert(JSON.stringify(detailData), {icon: 1});

                            var oldData = layui.table.cache["detailTable"];
                            console.log(newRow);
                            oldData.push(newRow);

                            layui.table.reload('detailTable',{
                                data: oldData
                            });

                            layui.layer.close(index);
                        },
                        cancel: function(){
                            //右上角关闭回调
                            //return false 开启该代码可禁止点击该按钮关闭
                        },
                        success: function () {
                            layui.form.val("project-detail-form", {
                                "rid" : "n-" +  (layui.table.cache["detailTable"].length + 1)
                                ,"material": ""
                                ,"length": ""
                                ,"width": ""
                                ,"quantity": ""
                                ,"price": ""
                                ,"note": ""
                            });
                        }
                    });
                }

            });

            //点击明细行，弹出修改窗口
            layui.table.on('row(detailTable)', function(obj){

                //标注选中样式
                obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');

                var rowData = obj.data;

                layui.layer.open({
                    // area: '700px',
                    type: 1,
                    title: '修改 - 项目明细：',
                    content: layui.jquery("#project-detail-form"),
                    btn: ['修改', '删除', '取消'],
                    btnAlign: 'c',
                    yes: function(index, layero) {
                        //按钮 [保存] 的回调
                        var formData = layui.form.val("project-detail-form");

                        var oldData = layui.table.cache["detailTable"];
                        console.log(formData);
                        // oldData.push(newRow);

                        for (var i = 0, row; i < oldData.length; i++) {
                            row = oldData[i];
                            if (row.rid == formData.rid) {
                                layui.jquery.extend(oldData[i], formData);
                                break;
                            }
                        }

                        layui.table.reload('detailTable',{
                            data: oldData
                        });

                        layui.layer.close(index);
                    },
                    cancel: function(){
                        //右上角关闭回调
                        //return false 开启该代码可禁止点击该按钮关闭
                    },
                    success: function () {
                        layui.form.val("project-detail-form", {
                            "id" : rowData.id
                            ,"material": rowData.material
                            ,"length": rowData.length
                            ,"width": rowData.width
                            ,"quantity": rowData.quantity
                            ,"price": rowData.price
                            ,"note": rowData.note
                        });
                    }
                });

            });

            //收费明细
            layui.table.render({
                elem: '#feeTable'
                ,defaultToolbar: [] //这里在右边显示
                ,toolbar: '#toolbarPay'
                , cols: [[ //标题栏
                    {field: 'id', title: 'ID', sort: true}
                    , {field: 'payNo', title: '收款单号'}
                    , {field: 'pay', title: '支付金额', align: 'right' }
                    , {field: 'payDate', title: '支付日期'}
                    , {field: 'type', title: '用途(定金或付款)', width: 140}
                    , {field: 'payee', title: '收款人'}
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

    var table = layui.table ,form = layui.form;

    layui.jquery.ajax({url:"/project/"+pid,
        success:function(response){

            layer.open({
                area: '700px',
                type: 1,
                title: '修改记录：',
                content: layui.jquery("#project-form"),
                btnAlign: 'c',
                btn: ['保存', '取消'],
                yes: function(index, layero){
                    //按钮 '保存' 的回调
                    var data1 = form.val("project-form");

                    // layer.alert(JSON.stringify(data1), {icon: 1});

                    layui.jquery.ajax({
                        type: 'put',
                        url: "/project",
                        data: JSON.stringify(data1),
                        dataType: "json",
                        contentType : 'application/json;charset=UTF-8',
                        success: function (res){
                            // layui.jquery("button#searchBtn").click();
                            searchProject();
                            layer.closeAll();
                        },
                        error: function (res) {
                            layer.alert(JSON.stringify(res.responseJSON.message), {icon: 2});
                        }
                    });

                    return false;// 开启该代码可禁止点击该按钮关闭
                },
                btn2: function(index, layero){
                    //按钮 '取消' 的回调
                    // return false;// 开启该代码可禁止点击该按钮关闭
                }
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                },
                success : function () {
                    //数据绑定

                    form.val("project-form", {
                        "id" : response.data.id
                        ,"name": response.data.name
                        ,"projectDate": response.data.projectDate
                        ,"cost": response.data.cost
                        ,"costPaid": response.data.costPaid
                        ,"processCost": response.data.processCost
                        ,"materialCost": response.data.materialCost
                        ,"beginDate": response.data.beginDate
                        ,"finishDate": response.data.finishDate
                        ,"serialNo": response.data.serialNo
                        ,"clientPhone": response.data.clientPhone
                        ,"clientAddress": response.data.clientAddress
                        ,"note": response.data.note
                    });

                    //项目明细
                    layui.table.render({
                        elem: '#detailTable'
                        , cols: [[ //标题栏
                            {field: 'id', title: 'ID', sort: true}
                            , {field: 'material', title: '材料'}
                            , {field: 'length', title: '长', align:'right' }
                            , {field: 'width', title: '宽', align:'right'}
                            , {field: 'quantity', title: '数量', align:'right'}
                            , {field: 'price', title: '单价', align:'right'}
                            , {field: 'note', title: '备注'}
                        ]]
                        , data: response.data.details
                    });

                    //收费明细
                    layui.table.render({
                        elem: '#feeTable'
                        , cols: [[ //标题栏
                            {field: 'id', title: 'ID', sort: true}
                            , {field: 'payNo', title: '收款单号'}
                            , {field: 'pay', title: '支付金额', align: 'right' }
                            , {field: 'payDate', title: '支付日期'}
                            , {field: 'type', title: '用途(定金或付款)', width: 140}
                            , {field: 'payee', title: '收款人'}
                        ]]
                        , data: response.data.pays
                    });

                    //数据绑定结束

                    // layui.table.on('edit(feeTable)', function(obj){
                    //     var value = obj.value //得到修改后的值
                    //         ,data = obj.data //得到所在行所有键值
                    //         ,field = obj.field; //得到字段
                    //     layer.msg('[ID: '+ data.id +'] 字段:' + field + ' event:' + obj.event + ' 更改为：'+ value);
                    //     // console.log(JSONString.)
                    //
                    // });

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
                        else if(obj.event === 'number') {
                            // var field = layui.jquery(this).data('field');
                            // var selector = obj.tr.selector+' td[data-field="'+obj.field+'"] div';
                            // var oldtext = layui.jquery(selector).text();
                            // //判断数据类型
                            // if(!isNumber(obj.value)) {
                            //     layer.msg('请输入数字');
                            //     console.log(oldtext);
                            //     // 重点 赋值
                            //     layui.jquery(obj.tr.selector + ' td[data-field="' + obj.field + '"] input').val(oldtext);
                            // }
                            console.log("edit number cell....")
                        }
                    });

                }

            });

        }});
}