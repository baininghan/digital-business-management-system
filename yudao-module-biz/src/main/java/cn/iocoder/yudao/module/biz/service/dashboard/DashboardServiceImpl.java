package cn.iocoder.yudao.module.biz.service.dashboard;

import cn.iocoder.yudao.module.biz.controller.admin.dashboard.DashboardSummaryRespVO;
import cn.iocoder.yudao.module.biz.dal.dataobject.BidDO;
import cn.iocoder.yudao.module.biz.dal.dataobject.ContractDO;
import cn.iocoder.yudao.module.biz.dal.dataobject.OpportunityDO;
import cn.iocoder.yudao.module.biz.dal.dataobject.ProjectDO;
import cn.iocoder.yudao.module.biz.dal.mysql.BidMapper;
import cn.iocoder.yudao.module.biz.dal.mysql.ContractMapper;
import cn.iocoder.yudao.module.biz.dal.mysql.OpportunityMapper;
import cn.iocoder.yudao.module.biz.dal.mysql.ProjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ContractMapper contractMapper;
    @Resource
    private OpportunityMapper opportunityMapper;
    @Resource
    private BidMapper bidMapper;

    @Override
    public DashboardSummaryRespVO getSummary(String month) {
        YearMonth yearMonth = month == null || month.length() == 0 ? YearMonth.now() : YearMonth.parse(month);
        LocalDate monthStart = yearMonth.atDay(1);
        LocalDate monthEnd = yearMonth.atEndOfMonth();

        List<ProjectDO> projects = projectMapper.selectList();
        List<ContractDO> contracts = contractMapper.selectList();
        List<OpportunityDO> opportunities = opportunityMapper.selectList();
        List<BidDO> bids = bidMapper.selectList();

        DashboardSummaryRespVO respVO = new DashboardSummaryRespVO();
        respVO.setMonthOutput(sumProjectOutput(projects));
        respVO.setMonthActualCost(sumProjectCost(projects));
        respVO.setMonthProfit(respVO.getMonthOutput().subtract(respVO.getMonthActualCost()));

        List<ContractDO> monthContracts = contracts.stream()
                .filter(contract -> contract.getSignDate() != null
                        && !contract.getSignDate().isBefore(monthStart)
                        && !contract.getSignDate().isAfter(monthEnd))
                .collect(java.util.stream.Collectors.toList());
        respVO.setNewContractCount((long) monthContracts.size());
        respVO.setNewContractAmount(sumContractAmount(monthContracts));
        respVO.setDueUnsettledAmount(sumDueUnsettledAmount(contracts));
        respVO.setReceivableUnreceivedAmount(sumReceivableUnreceivedAmount(contracts));

        respVO.setTrackingOpportunityCount((long) opportunities.size());
        respVO.setTrackingOpportunityAmount(sumOpportunityAmount(opportunities));
        respVO.setBidCount((long) bids.size());
        respVO.setBidAmount(sumBidAmount(bids));
        respVO.setWonBidAmount(sumBidAmountByWinStatus(bids, "WON"));
        respVO.setConvertedContractAmount(sumConvertedBidAmount(bids));
        return respVO;
    }

    private BigDecimal sumProjectOutput(List<ProjectDO> projects) {
        return projects.stream().map(ProjectDO::getAccumulatedOutput).filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumProjectCost(List<ProjectDO> projects) {
        return projects.stream().map(ProjectDO::getActualCost).filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumContractAmount(List<ContractDO> contracts) {
        return contracts.stream().map(ContractDO::getAmount).filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumDueUnsettledAmount(List<ContractDO> contracts) {
        return contracts.stream()
                .map(contract -> nullToZero(contract.getDueSettleAmount()).subtract(nullToZero(contract.getSettledAmount())))
                .filter(amount -> amount.compareTo(BigDecimal.ZERO) > 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumReceivableUnreceivedAmount(List<ContractDO> contracts) {
        return contracts.stream()
                .map(contract -> nullToZero(contract.getInvoicedAmount()).subtract(nullToZero(contract.getReceivedAmount())))
                .filter(amount -> amount.compareTo(BigDecimal.ZERO) > 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumOpportunityAmount(List<OpportunityDO> opportunities) {
        return opportunities.stream().map(OpportunityDO::getEstimatedAmount).filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumBidAmount(List<BidDO> bids) {
        return bids.stream().map(BidDO::getAmount).filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumBidAmountByWinStatus(List<BidDO> bids, String winStatus) {
        return bids.stream().filter(bid -> winStatus.equals(bid.getWinStatus()))
                .map(BidDO::getAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumConvertedBidAmount(List<BidDO> bids) {
        return bids.stream().filter(bid -> Boolean.TRUE.equals(bid.getConvertedToContract()))
                .map(BidDO::getAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal nullToZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

}
