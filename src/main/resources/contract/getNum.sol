pragma solidity ^0.7.0;

contract NumTest{
    uint256 num;

    function store(uint256 _num) public {
        num = _num;
    }


    function getNum() public view returns(uint256){
        return num;
    }
}