//
//  ShadowUtil.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

private let PI:               CGFloat   =   CGFloat(Double.pi)
private let FIVE_PI_BY_FOUR:  CGFloat   =   CGFloat((5 * PI) / 4)
private let THREE_PI_BY_TWO:  CGFloat   =   CGFloat((3 * PI) / 2)
private let SEVEN_PI_BY_FOUR: CGFloat   =   CGFloat((7 * PI) / 4)
private let TWO_PI:           CGFloat   =   CGFloat(2 * PI)
private let PI_BY_FOUR:       CGFloat   =   CGFloat(PI / 4)
private let PI_BY_TWO:        CGFloat   =   CGFloat(PI / 2)
private let THREE_PI_BY_FOUR: CGFloat   =   CGFloat((3 * PI) / 4)

public enum ShadowTraits {
  case defaultTraits
  case customTraits(color: CGColor, opacity: Float, offset: CGSize, radius: CGFloat)
  
  public func getTraits() -> (color: CGColor, opacity: Float, offset: CGSize, radius: CGFloat) {
    switch self {
    case .defaultTraits:
      return (color: UIColor.darkGray.cgColor,
              opacity: 0,
              offset: CGSize(width: 0, height: 2),
              radius: 2)
    case .customTraits(let color, let opacity, let offset, let radius):
      return (color, opacity, offset, radius)
    }
  }
}

public enum ShadowSides {
  case noShadow
  case leftOnly
  case topOnly
  case rightOnly
  case bottomOnly
  case leftAndRight
  case topAndBottom
  case leftTopAndRight
  case leftBottomAndRight
  case allSides
  
  func getPath(forSize size: CGSize,andCornerRadius radius: CGFloat = 0.0, maskedCorners: CACornerMask) -> UIBezierPath {
    var rad: CGFloat = radius
    
    /* Left only */
    func getPath_LeftOnly() -> UIBezierPath {
      let path: UIBezierPath = UIBezierPath()
      rad = maskedCorners.contains(.layerMinXMinYCorner) ? radius : 0.0
      path.move(to: CGPoint(x: 0 + rad / 2, y: 0 + rad / 2))
      path.addArc(withCenter: CGPoint(x: rad, y: rad),
                  radius: rad,
                  startAngle: FIVE_PI_BY_FOUR,
                  endAngle: PI,
                  clockwise: false)
      rad = maskedCorners.contains(.layerMinXMaxYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: 0, y: size.height - rad))
      path.addArc(withCenter: CGPoint(x: rad, y: size.height - rad),
                  radius: rad,
                  startAngle: PI,
                  endAngle: THREE_PI_BY_FOUR,
                  clockwise: false)
      path.addLine(to: CGPoint(x: size.width / 4, y: size.height / 2))
      path.close()
      return path
    }
    
    /* Top Only */
    func getPath_TopOnly() -> UIBezierPath {
      let path: UIBezierPath = UIBezierPath()
      rad = maskedCorners.contains(.layerMinXMinYCorner) ? radius : 0.0
      path.move(to: CGPoint(x: 0 + rad / 2, y: 0 + rad / 2))
      path.addArc(withCenter: CGPoint(x: rad, y: rad),
                  radius: rad,
                  startAngle: FIVE_PI_BY_FOUR,
                  endAngle: THREE_PI_BY_TWO,
                  clockwise: true)
      rad = maskedCorners.contains(.layerMaxXMinYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width - rad, y: 0))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: rad),
                  radius: rad,
                  startAngle: THREE_PI_BY_TWO,
                  endAngle: SEVEN_PI_BY_FOUR,
                  clockwise: true)
      path.addLine(to: CGPoint(x: size.width / 2, y: size.height / 4))
      path.close()
      return path
    }
    
    /* Right Only */
    func getPath_RightOnly() -> UIBezierPath {
      let path: UIBezierPath = UIBezierPath()
      rad = maskedCorners.contains(.layerMaxXMinYCorner) ? radius : 0.0
      path.move(to: CGPoint(x: size.width - rad / 2, y: 0 + rad / 2))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: rad),
                  radius: rad,
                  startAngle: SEVEN_PI_BY_FOUR,
                  endAngle: TWO_PI,
                  clockwise: true)
      rad = maskedCorners.contains(.layerMaxXMaxYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width, y: size.height - rad))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: size.height - rad),
                  radius: rad,
                  startAngle: TWO_PI,
                  endAngle: PI_BY_FOUR,
                  clockwise: true)
      path.addLine(to: CGPoint(x: size.width / 1.25, y: size.height / 2))
      path.close()
      return path
    }
    
    /* Bottom Only */
    func getPath_BottomOnly() -> UIBezierPath {
      let path: UIBezierPath = UIBezierPath()
      rad = maskedCorners.contains(.layerMinXMaxYCorner) ? radius : 0.0
      path.move(to: CGPoint(x: rad / 2, y: size.height - rad / 2))
      path.addArc(withCenter: CGPoint(x: rad, y: size.height - rad),
                  radius: rad,
                  startAngle: THREE_PI_BY_FOUR,
                  endAngle: PI_BY_TWO,
                  clockwise: false)
      rad = maskedCorners.contains(.layerMaxXMaxYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width - rad, y: size.height))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: size.height - rad),
                  radius: rad,
                  startAngle: PI_BY_TWO,
                  endAngle: PI_BY_FOUR,
                  clockwise: false)
      path.addLine(to: CGPoint(x: size.width / 2, y: size.height / 1.25))
      path.close()
      return path
    }
    
    /* Left and Right */
    func getPath_LeftAndRight() -> UIBezierPath {
      let path: UIBezierPath = UIBezierPath()
      rad = maskedCorners.contains(.layerMinXMinYCorner) ? radius : 0.0
      path.move(to: CGPoint(x: 0 + rad / 2, y: 0 + rad / 2))
      path.addArc(withCenter: CGPoint(x: rad, y: rad),
                  radius: rad,
                  startAngle: FIVE_PI_BY_FOUR,
                  endAngle: PI,
                  clockwise: false)
      rad = maskedCorners.contains(.layerMinXMaxYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: 0, y: size.height - rad))
      path.addArc(withCenter: CGPoint(x: rad, y: size.height - rad),
                  radius: rad,
                  startAngle: PI,
                  endAngle: THREE_PI_BY_FOUR,
                  clockwise: false)
      path.addLine(to: CGPoint(x: size.width * 0.1, y: size.height / 2))
      path.addLine(to: CGPoint(x: size.width * 0.9, y: size.height / 2))
      rad = maskedCorners.contains(.layerMaxXMinYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width - rad / 2, y: 0 + rad / 2))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: rad),
                  radius: rad,
                  startAngle: SEVEN_PI_BY_FOUR,
                  endAngle: TWO_PI,
                  clockwise: true)
      rad = maskedCorners.contains(.layerMaxXMaxYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width, y: size.height - rad))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: size.height - rad),
                  radius: rad,
                  startAngle: TWO_PI,
                  endAngle: PI_BY_FOUR,
                  clockwise: true)
      path.addLine(to: CGPoint(x: size.width * 0.9, y: size.height / 2))
      path.addLine(to: CGPoint(x: size.width * 0.1, y: size.height / 2))
      path.close()
      return path
    }
    
    /* Top and Bottom */
    func getPath_TopAndBottom() -> UIBezierPath {
      let path: UIBezierPath = UIBezierPath()
      rad = maskedCorners.contains(.layerMinXMinYCorner) ? radius : 0.0
      path.move(to: CGPoint(x: 0 + rad / 2, y: 0 + rad / 2))
      path.addArc(withCenter: CGPoint(x: rad, y: rad),
                  radius: rad,
                  startAngle: FIVE_PI_BY_FOUR,
                  endAngle: THREE_PI_BY_TWO,
                  clockwise: true)
      rad = maskedCorners.contains(.layerMaxXMinYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width - rad, y: 0))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: rad),
                  radius: rad,
                  startAngle: THREE_PI_BY_TWO,
                  endAngle: SEVEN_PI_BY_FOUR,
                  clockwise: true)
      path.addLine(to: CGPoint(x: size.width / 2, y: size.height * 0.2))
      path.addLine(to: CGPoint(x: size.width / 2, y: size.height * 0.8))
      rad = maskedCorners.contains(.layerMinXMaxYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: rad / 2, y: size.height - rad / 2))
      path.addArc(withCenter: CGPoint(x: rad, y: size.height - rad),
                  radius: rad,
                  startAngle: THREE_PI_BY_FOUR,
                  endAngle: PI_BY_TWO,
                  clockwise: false)
      rad = maskedCorners.contains(.layerMaxXMaxYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width - rad, y: size.height))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: size.height - rad),
                  radius: rad,
                  startAngle: PI_BY_TWO,
                  endAngle: PI_BY_FOUR,
                  clockwise: false)
      path.addLine(to: CGPoint(x: size.width / 2, y: size.height * 0.8))
      path.addLine(to: CGPoint(x: size.width / 2, y: size.height * 0.2))
      path.close()
      return path
    }
    
    /* Left, Top and Right */
    func getPath_LeftTopAndRight() -> UIBezierPath {
      let path: UIBezierPath = UIBezierPath()
      rad = maskedCorners.contains(.layerMinXMaxYCorner) ? radius : 0.0
      path.move(to: CGPoint(x: rad / 2, y: size.height - rad / 2))
      path.addArc(withCenter: CGPoint(x: rad, y: size.height - rad),
                  radius: rad,
                  startAngle: THREE_PI_BY_FOUR,
                  endAngle: PI,
                  clockwise: true)
      rad = maskedCorners.contains(.layerMinXMinYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: 0, y: rad))
      path.addArc(withCenter: CGPoint(x: rad, y: rad),
                  radius: rad,
                  startAngle: PI,
                  endAngle: THREE_PI_BY_TWO,
                  clockwise: true)
      rad = maskedCorners.contains(.layerMaxXMinYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width - rad, y: 0))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: rad),
                  radius: rad,
                  startAngle: THREE_PI_BY_TWO,
                  endAngle: TWO_PI,
                  clockwise: true)
      rad = maskedCorners.contains(.layerMaxXMaxYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width, y: size.height - rad))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: size.height - rad),
                  radius: rad,
                  startAngle: TWO_PI,
                  endAngle: PI_BY_FOUR,
                  clockwise: true)
      path.addLine(to: CGPoint(x: size.width * 0.9, y: size.height * 0.1))
      path.addLine(to: CGPoint(x: size.width * 0.1, y: size.height * 0.1))
      path.close()
      return path
    }
    
    /* Left, Bottom and Right */
    func getPath_LeftBottomAndRight() -> UIBezierPath {
      let path: UIBezierPath = UIBezierPath()
      rad = maskedCorners.contains(.layerMinXMinYCorner) ? radius : 0.0
      path.move(to: CGPoint(x: 0 + rad / 2, y: 0 + rad / 2))
      path.addArc(withCenter: CGPoint(x: rad, y: rad),
                  radius: rad,
                  startAngle: FIVE_PI_BY_FOUR,
                  endAngle: PI,
                  clockwise: false)
      rad = maskedCorners.contains(.layerMinXMaxYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: 0, y: size.height - rad))
      path.addArc(withCenter: CGPoint(x: rad, y: size.height - rad),
                  radius: rad,
                  startAngle: PI,
                  endAngle: PI_BY_TWO,
                  clockwise: false)
      rad = maskedCorners.contains(.layerMaxXMaxYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width - rad, y: size.height))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: size.height - rad),
                  radius: rad,
                  startAngle: PI_BY_TWO,
                  endAngle: TWO_PI,
                  clockwise: false)
      rad = maskedCorners.contains(.layerMaxXMinYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width, y: rad))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: rad),
                  radius: rad,
                  startAngle: TWO_PI,
                  endAngle: SEVEN_PI_BY_FOUR,
                  clockwise: false)
      path.addLine(to: CGPoint(x: size.width * 0.9, y: size.height * 0.9))
      path.addLine(to: CGPoint(x: size.width * 0.1, y: size.height * 0.9))
      path.close()
      return path
    }
    
    /* All sides */
    func getPath_AllSides() -> UIBezierPath {
      let path: UIBezierPath = UIBezierPath()
      rad = maskedCorners.contains(.layerMinXMaxYCorner) ? radius : 0.0
      path.move(to: CGPoint(x: rad, y: size.height))
      path.addArc(withCenter: CGPoint(x: rad, y: size.height - rad),
                  radius: rad,
                  startAngle: PI_BY_TWO,
                  endAngle: PI,
                  clockwise: true)
      rad = maskedCorners.contains(.layerMinXMinYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: 0, y: rad))
      path.addArc(withCenter: CGPoint(x: rad, y: rad),
                  radius: rad,
                  startAngle: PI,
                  endAngle: THREE_PI_BY_TWO,
                  clockwise: true)
      rad = maskedCorners.contains(.layerMaxXMinYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width - rad, y: 0))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: rad),
                  radius: rad,
                  startAngle: THREE_PI_BY_TWO,
                  endAngle: TWO_PI,
                  clockwise: true)
      rad = maskedCorners.contains(.layerMaxXMaxYCorner) ? radius : 0.0
      path.addLine(to: CGPoint(x: size.width, y: size.height - rad))
      path.addArc(withCenter: CGPoint(x: size.width - rad, y: size.height - rad),
                  radius: rad,
                  startAngle: TWO_PI,
                  endAngle: PI_BY_TWO,
                  clockwise: true)
      path.close()
      return path
    }
    
    switch self {
    case .noShadow: return UIBezierPath()
    case .leftOnly: return getPath_LeftOnly()
    case .topOnly: return getPath_TopOnly()
    case .rightOnly: return getPath_RightOnly()
    case .bottomOnly: return getPath_BottomOnly()
    case .leftAndRight: return getPath_LeftAndRight()
    case .topAndBottom: return getPath_TopAndBottom()
    case .leftTopAndRight: return getPath_LeftTopAndRight()
    case .leftBottomAndRight: return getPath_LeftBottomAndRight()
    case .allSides: return getPath_AllSides()
    }
  }
}

public class ShadowUtility {
  
  static public func applyShadow(toView view: UIView,
                                 onSides sides: ShadowSides,
                                 shadowTraits traits: ShadowTraits) {
    DispatchQueue.main.async {
      
      view.layer.needsDisplayOnBoundsChange = true
      view.layer.shadowPath = sides.getPath(forSize: view.frame.size, andCornerRadius: view.layer.cornerRadius, maskedCorners: view.layer.maskedCorners).cgPath
      view.layer.shadowColor = traits.getTraits().color
      view.layer.shadowOpacity = traits.getTraits().opacity
      view.layer.shadowOffset = traits.getTraits().offset
      view.layer.shadowRadius = traits.getTraits().radius
      view.layer.shouldRasterize = true
      view.layer.rasterizationScale = UIScreen.main.scale
      
    }
  }

}
