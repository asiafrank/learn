package com.asiafrank.bangumi.core

/**
  * Page
  *
  * Created at 31/1/2017.
  *
  * @param elements  content elements
  * @param pageNum  current pageNumber
  * @param pageSize max number of elements that one page can contain
  * @param totalElementsNum total number of elements in database
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class Page[T](val elements: List[T], val pageNum: Int, val pageSize: Int, val totalElementsNum: Int) extends Serializable {

  def totalPage(): Int = {
    if (totalElementsNum == 0) 1 else (pageSize + totalElementsNum - 1) / pageSize
  }

  def firstPageNum(): Int = 1

  def lastPageNum(): Int = totalPage()

  def nextPageNum(): Int = pageNum + 1

  def previousPageNum(): Int = pageNum - 1

  def isFirstPage(): Boolean = pageNum == firstPageNum

  def isLastPage(): Boolean = !hasNext

  def hasPrevious(): Boolean = !isFirstPage

  def hasNext(): Boolean = {
    pageNum + 1 < totalPage
  }
}

object Page {
  def apply[T](elements: List[T], pageNum: Int, pageSize: Int, total: Int) = new Page(elements, pageNum, pageSize, total)
}


