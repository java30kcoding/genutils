package cn.itlou.genutils.tableexcel;

import cn.itlou.genutils.model.TableColModel;
import cn.itlou.genutils.model.TableTemplateModel;
import cn.itlou.genutils.oracle.OracleExcelMapper;
import cn.itlou.genutils.pg.PGExcelMapper;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanyl
 * @date 2020/7/28 15:56
 **/
@Service
public class ExcelService {

    @Resource
    PGExcelMapper pgExcelMapper;
    @Resource
    OracleExcelMapper oracleExcelMapper;

    private static String[] tableNameArr = {"t_fpc_dzpp" ,"t_fpc_dzpp_json" ,"t_fpc_dzpp_mx" ,"t_fpc_fpcyxx" ,"t_fpc_hy" ,"t_fpc_hy_json" ,"t_fpc_hy_mx" ,"t_fpc_jdcxs" ,"t_fpc_jdcxs_json" ,"t_fpc_jspp" ,"t_fpc_jspp_json" ,"t_fpc_jspp_mx" ,"t_fpc_pp" ,"t_fpc_pp_json" ,"t_fpc_pp_mx" ,"t_fpc_txfdzfp" ,"t_fpc_txfdzfp_json" ,"t_fpc_txfdzfp_mx" ,"t_fpc_zp" ,"t_fpc_zp_json" ,"t_fpc_zp_mx" ,"t_intf_jx_bx" ,"t_intf_jx_cb" ,"t_intf_jx_lp" ,"t_intf_jx_yj" ,"t_intf_jx_zb" ,"t_jx_manual" ,"t_jx_rz", "org_code_relation" ,"t_bx_intax" ,"t_bx_intax_orgcode" ,"t_bx_intax_sap_push" ,"t_dms_currency" ,"t_dms_exch" ,"t_fpc_zp_manual" ,"t_intf_log_get_jx_bx" ,"t_intf_sap_gsdm" ,"t_intf_sap_gsdm_push" ,"t_intf_sap_gsdm_ywgsjg" ,"t_intf_sap_gsdm_ywgsjg_push" ,"t_intf_sap_sgsdm" ,"t_intf_sap_sgsdm_push" ,"t_jx_archlog" ,"t_jx_astat" ,"t_jx_authentication" ,"t_jx_bmd" ,"t_jx_differentt" ,"t_jx_hmd" ,"t_jx_invoicelog" ,"t_jx_manual_interim" ,"t_jx_mgc" ,"t_jx_mgc_jyjg" ,"t_jx_pdfmanual" ,"t_jx_sqxx" ,"t_log_autocheck" ,"t_log_bgtask_run" ,"t_ls_jx_qy_fp_dxg" ,"t_sff_intax_cb" ,"t_sff_intax_lp" ,"t_sff_intax_yj" ,"t_sff_intax_zb" ,"t_sys_common_property" ,"t_sys_dic" ,"t_sys_menu" ,"t_sys_org" ,"t_sys_role" ,"t_sys_role_with_menu" ,"t_sys_taxno_inputtax" ,"t_sys_user" ,"t_sys_user_with_org" ,"t_sys_user_with_role" ,"t_sys_versions" ,"t_tj_jxse" ,"t_tj_wdkse" ,"vms_channel_code_relation" ,"vms_sap_fnc_sys_subj_data_intf"};

    public void genAll(){
        for (String tableName : tableNameArr) {
            genExcel(tableName);
        }
    }

    public void genExcel(String tableName){
        String fileName = "D:\\tmp\\test" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        List<TableColModel> pgTable = pgExcelMapper.getTableInfo(tableName);
        System.out.println(pgTable);
        List<TableColModel> oracleTable = oracleExcelMapper.getTableInfo(tableName);
        List<TableColModel> result = Lists.newArrayList();
        if (pgTable.size() > oracleTable.size()) {
            for (int i = 0; i < oracleTable.size(); i++) {
                pgTable.get(i).setOldCol(oracleTable.get(i).getOldCol());
                pgTable.get(i).setOldColType(oracleTable.get(i).getOldColType());
            }
            result = pgTable;
        } else {
            for (int i = 0; i < pgTable.size(); i++) {
                oracleTable.get(i).setNewCol(pgTable.get(i).getNewCol());
                oracleTable.get(i).setNewColType(pgTable.get(i).getNewColType());
            }
            result = oracleTable;
        }
        for (TableColModel model : result) {
            if (StringUtils.isEmpty(model.getNewCol())) {
                model.setNewCol("");
            }
            if (StringUtils.isEmpty(model.getOldCol())) {
                model.setOldCol("");
            }
            if (StringUtils.isEmpty(model.getNewColType())) {
                model.setNewColType("");
            }
            if (StringUtils.isEmpty(model.getOldColType())) {
                model.setOldColType("");
            }
            if (StringUtils.isEmpty(model.getRemark())) {
                model.setRemark("");
            }
        }
//            tableColModel.setOldCol(oracleTable);
        System.out.println(oracleTable);
        TableTemplateModel tableTemplateModel = new TableTemplateModel();
        List<String> oldCol = Lists.newArrayList();
        List<String> oldColType = Lists.newArrayList();
        List<String> newCol = Lists.newArrayList();
        List<String> newColType = Lists.newArrayList();
        List<String> remark = Lists.newArrayList();
        for (TableColModel model : pgTable) {
            oldCol.add(model.getOldCol());
            oldColType.add(model.getOldColType());
            newCol.add(model.getNewCol());
            newColType.add(model.getNewColType());
            remark.add(model.getRemark());
        }
        tableTemplateModel.setOldCol(oldCol);
        tableTemplateModel.setOldColType(oldColType);
        tableTemplateModel.setNewCol(newCol);
        tableTemplateModel.setNewColType(newColType);
        tableTemplateModel.setRemark(remark);

        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate("D:\\temp\\template.xlsx").build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(result, fillConfig, writeSheet);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tableName", pgTable.get(0).getTableName());
        excelWriter.fill(map, writeSheet);
        excelWriter.finish();

    }

}
