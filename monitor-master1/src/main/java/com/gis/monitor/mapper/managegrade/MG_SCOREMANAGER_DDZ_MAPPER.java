package com.gis.monitor.mapper.managegrade;

import com.gis.monitor.pojo.managegrade.MG_SCOREMANAGER_DDZ;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface MG_SCOREMANAGER_DDZ_MAPPER {

    List<MG_SCOREMANAGER_DDZ> getSubjectList();

    List<MG_SCOREMANAGER_DDZ> getAll();

    List<MG_SCOREMANAGER_DDZ> getWDX();

    List<MG_SCOREMANAGER_DDZ> getLXX();

    List<MG_SCOREMANAGER_DDZ> getGCJD();

    List<MG_SCOREMANAGER_DDZ> getNBTZ();

    List<MG_SCOREMANAGER_DDZ> getGCCD();

    List<MG_SCOREMANAGER_DDZ> getQSWGCLXX();

    List<MG_SCOREMANAGER_DDZ> getQSWGCCD();

    List<MG_SCOREMANAGER_DDZ> getJYLGCLXX();

    List<MG_SCOREMANAGER_DDZ> getJYLGCCD();

    List<MG_SCOREMANAGER_DDZ> getWDGCLXX();

    List<MG_SCOREMANAGER_DDZ> getWDGCCD();

    List<MG_SCOREMANAGER_DDZ> getmouthgrade();

    List<MG_SCOREMANAGER_DDZ> getElectricityRatio();

    List<MG_SCOREMANAGER_DDZ> getResistanceCotton();

    List<MG_SCOREMANAGER_DDZ> getTouchResistance();

    List<MG_SCOREMANAGER_DDZ> getTouchResistanceOne();

    List<MG_SCOREMANAGER_DDZ> getOutElectricity();

    List<MG_SCOREMANAGER_DDZ> getWBYS();

    List<MG_SCOREMANAGER_DDZ> getDLWDD();

    List<MG_SCOREMANAGER_DDZ> getGCYQ();

    List<MG_SCOREMANAGER_DDZ> getJJZF();

    List<MG_SCOREMANAGER_DDZ> getSSLGRY();

    List<MG_SCOREMANAGER_DDZ> getZRZF();

    List<MG_SCOREMANAGER_DDZ> getZRLGRY();






}
